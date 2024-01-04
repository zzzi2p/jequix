package net.i2p.pow.hashx;

import static net.i2p.pow.hashx.EPort.*;
import static net.i2p.pow.hashx.GenCtx.*;
import static net.i2p.pow.hashx.IType.*;

class Program {

    private static final boolean TRACE = false;
    static final int BRANCH_MASK = 0x80000000;

    /* If the instruction is a multiplication.  */
    private static boolean is_mul(IType type) {
        return type.ordinal() <= INSTR_MUL_R.ordinal();
    }

    private static ITmpl select_template(GenCtx ctx, IType last_instr, int attempt) {
        PItem item = PLayout.layout[ctx.sub_cycle % 36];
        ITmpl tpl;
        do {
            int index = (item.mask0 != 0) ? ctx.gen.nextByte() & (attempt > 0 ? item.mask1 : item.mask0) : 0;
            tpl = item.templates[index];
        } while (!item.dups && tpl.group == last_instr);
        return tpl;
    }

    private static int branch_mask(SipHashRNG gen) {
        int mask = 0;
        int popcnt = 0;
        while (popcnt < LOG2_BRANCH_PROB) {
            int bit = gen.nextByte() & 0x1f;
            int bitmask = 1 << bit;
            if ((mask & bitmask) == 0) {
                mask |= bitmask;
                popcnt++;
            }
        }
        return mask;
    }

    private static void instr_from_template(ITmpl tpl, SipHashRNG gen, Instr instr) {
        instr.opcode = tpl.type;
        if (tpl.imm_mask != 0) {
            if (tpl.imm_mask == BRANCH_MASK) {
                instr.imm32 = branch_mask(gen);
            } else {
                do {
                    instr.imm32 = (int) (gen.nextInt() & tpl.imm_mask);
                } while (instr.imm32 == 0 && !tpl.imm_0);
            }
        }
        if (!tpl.op_par_src) {
            if (tpl.distinct_dst) {
                instr.op_par = -1;
            } else {
                instr.op_par = (int) gen.nextInt();
            }
        }
        if (!tpl.has_src)
            instr.src = -1;
        if (!tpl.has_dst)
            instr.dst = -1;
    }

    /**
     *  @return -1 on failure
     */
    private static int select_register(int[] available_regs, int regs_count, SipHashRNG gen) {
        if (regs_count == 0)
            return -1;
        int index;
        if (regs_count > 1)
            index = (int) (gen.nextInt() % regs_count);
        else
            index = 0;
        return available_regs[index];
    }

    /**
     *  @return success
     */
    private static boolean select_destination(ITmpl tpl, Instr instr, GenCtx ctx, int cycle) {
        int available_regs[] = new int[8];
        int regs_count = 0;
        /* Conditions for the destination register:
        // * value must be ready at the required cycle
        // * cannot be the same as the source register unless the instruction allows it
        //   - this avoids optimizable instructions such as "xor r, r" or "sub r, r"
        // * register cannot be multiplied twice in a row unless chain_mul is true
        //   - this avoids accumulation of trailing zeroes in registers due to excessive multiplication
        //   - allowChainedMul is set to true if an attempt to find source/destination registers failed (this is quite rare, but prevents a catastrophic failure of the generator)
        // * either the last instruction applied to the register or its source must be different than this instruction
        //   - this avoids optimizable instruction sequences such as "xor r1, r2; xor r1, r2" or "ror r, C1; ror r, C2" or "add r, C1; add r, C2"
        // * register r5 cannot be the destination of the IADD_RS instruction (limitation of the x86 lea instruction) */
        for (int i = 0; i < 8; ++i) {
            boolean available = ctx.registers[i].latency <= cycle;
            available &= ((!tpl.distinct_dst) || (i != instr.src));
            available &= (ctx.chain_mul || (tpl.group != INSTR_MUL_R) || (ctx.registers[i].last_op != INSTR_MUL_R));
            available &= ((ctx.registers[i].last_op != tpl.group) || (ctx.registers[i].last_op_par != instr.op_par));
            available &= ((instr.opcode != INSTR_ADD_RS) || (i != REGISTER_NEEDS_DISPLACEMENT));
            if (available)
                available_regs[regs_count++] = i;
        }
        int dst = select_register(available_regs, regs_count, ctx.gen);
        if (dst < 0) {
            if (TRACE) printf("No reg available out of %d\n", regs_count);
            return false;
        }
        instr.dst = dst;
        return true;
}

    /**
     *  @return success
     */
    private static boolean select_source(ITmpl tpl, Instr instr, GenCtx ctx, int cycle) {
        int available_regs[] = new int[8];
        int regs_count = 0;
        /* all registers that are ready at the cycle */
        for (int i = 0; i < 8; ++i) {
            if (ctx.registers[i].latency <= cycle)
                available_regs[regs_count++] = i;
        }
        /* if there are only 2 available registers for ADD_RS and one of them is r5, select it as the source because it cannot be the destination */
        if (regs_count == 2 && instr.opcode == INSTR_ADD_RS) {
            if (available_regs[0] == REGISTER_NEEDS_DISPLACEMENT || available_regs[1] == REGISTER_NEEDS_DISPLACEMENT) {
                instr.op_par = instr.src = REGISTER_NEEDS_DISPLACEMENT;
                return true;
            }
        }
        int src = select_register(available_regs, regs_count, ctx.gen);
        if (src >= 0) {
            instr.src = src;
            if (tpl.op_par_src)
                instr.op_par = src;
            return true;
        }
        return false;
    }

    /**
     *  @return new cycle or -1 on failure
     */
    private static int schedule_uop(EPort uop, GenCtx ctx, int cycle, boolean commit) {
        /* The scheduling here is done optimistically by checking port availability in order P5 . P0 . P1 to not overload
           port P1 (multiplication) by instructions that can go to any port. */
        for (; cycle < PORT_MAP_SIZE; ++cycle) {
            if ((uop.port & PORT_P5.port) != 0 && ctx.ports[cycle][2] == PORT_NONE) {
                if (commit) {
                    ctx.ports[cycle][2] = uop;
                }
                if (TRACE) TRACE_PRINT("%s scheduled to port P5 at cycle %s (commit = %s)\n", uop, cycle, commit);
                return cycle;
            }
            if ((uop.port & PORT_P0.port) != 0 && ctx.ports[cycle][0] == PORT_NONE) {
                if (commit) {
                    ctx.ports[cycle][0] = uop;
                }
                if (TRACE) TRACE_PRINT("%s scheduled to port P0 at cycle %s (commit = %s)\n", uop, cycle, commit);
                return cycle;
            }
            if ((uop.port & PORT_P1.port) != 0 && ctx.ports[cycle][1] == PORT_NONE) {
                if (commit) {
                    ctx.ports[cycle][1] = uop;
                }
                if (TRACE) TRACE_PRINT("%s scheduled to port P1 at cycle %s (commit = %s)\n", uop, cycle, commit);
                return cycle;
            }
        }
        return -1;
    }

    /**
     *  @return -1 on failure
     */
    private static int schedule_instr(ITmpl tpl, GenCtx ctx, boolean commit) {
        if (tpl.uop2 == PORT_NONE) {
            /* this instruction has only one uOP */
            return schedule_uop(tpl.uop1, ctx, ctx.cycle, commit);
        } else {
            /* instructions with 2 uOPs are scheduled conservatively by requiring both uOPs to execute in the same cycle */
            for (int cycle = ctx.cycle; cycle < PORT_MAP_SIZE; ++cycle) {
                int cycle1 = schedule_uop(tpl.uop1, ctx, cycle, false);
                int cycle2 = schedule_uop(tpl.uop2, ctx, cycle, false);
                if (cycle1 >= 0 && cycle1 == cycle2) {
                    if (commit) {
                        schedule_uop(tpl.uop1, ctx, cycle, true);
                        schedule_uop(tpl.uop2, ctx, cycle, true);
                    }
                    return cycle1;
                }
            }
        }
        return -1;
    }

    private static void print_registers(GenCtx ctx) {
        for (int i = 0; i < 8; ++i) {
            TRACE_PRINT("   R%s = %s %s\n", i, ctx.registers[i].latency, ctx.registers[i].last_op);
        }
    }

    /**
     *  @return success
     */
    static boolean generate(long[] keys, HXCtx program) {
        GenCtx ctx = new GenCtx(keys);
        program.code_size = 0;

        int attempt = 0;
        IType last_instr = null;

        while (program.code_size < HASHX_PROGRAM_MAX_SIZE) {
            Instr instr = program.code[program.code_size];
            if (TRACE) TRACE_PRINT("CYCLE: %s/%s\n", ctx.sub_cycle, ctx.cycle);

            /* select an instruction template */
            ITmpl tpl = select_template(ctx, last_instr, attempt);
            last_instr = tpl.group;
            if (TRACE) TRACE_PRINT("Template: %s\n", tpl);
            instr_from_template(tpl, ctx.gen, instr);

            /* calculate the earliest cycle when this instruction (all of its uOPs) can be scheduled for execution */
            int scheduleCycle = schedule_instr(tpl, ctx, false);
            if (scheduleCycle < 0) {
                if (TRACE) TRACE_PRINT("Unable to map operation '%s' to execution port (cycle %s)\n", tpl.x86_asm, ctx.cycle);
                break;
            }

            ctx.chain_mul = attempt > 0;

            /* find a source register (if applicable) that will be ready when this instruction executes */
            if (tpl.has_src) {
                if (!select_source(tpl, instr, ctx, scheduleCycle)) {
                    if (TRACE) TRACE_PRINT("; src STALL (attempt %s)\n", attempt);
                    if (attempt++ < MAX_RETRIES) {
                        continue;
                    }
                    if (TRACE) {
                        printf("; select_source FAILED at cycle %s\n", ctx.cycle);
                        print_registers(ctx);
                    }
                    ctx.sub_cycle += 3;
                    ctx.cycle = ctx.sub_cycle / 3;
                    attempt = 0;
                    continue;
                }
                if (TRACE) TRACE_PRINT("; src = r%s\n", instr.src);
            }
    
            /* find a destination register that will be ready when this instruction executes */
            if (tpl.has_dst) {
                if (!select_destination(tpl, instr, ctx, scheduleCycle)) {
                    if (TRACE) {
                        TRACE_PRINT("; dst STALL for %s (attempt %s)\n", tpl, attempt);
                        print_registers(ctx);
                    }
                    if (attempt++ < MAX_RETRIES) {
                        continue;
                    }
                    if (TRACE) {
                        TRACE_PRINT("; select_destination for %s FAILED at cycle %s\n", tpl, ctx.cycle);
                        print_registers(ctx);
                    }
                    ctx.sub_cycle += 3;
                    ctx.cycle = ctx.sub_cycle / 3;
                    attempt = 0;
                    continue;
                }
                if (TRACE) TRACE_PRINT("; dst = r%s\n", instr.dst);
            }
            attempt = 0;

            /* recalculate when the instruction can be scheduled for execution based on operand availability */
            scheduleCycle = schedule_instr(tpl, ctx, true);

            if (scheduleCycle < 0) {
                if (TRACE) TRACE_PRINT("Unable to map operation '%s' to execution port (cycle %s)\n", tpl.x86_asm, ctx.cycle);
                break;
            }

            /* terminating condition */
            if (scheduleCycle >= TARGET_CYCLE) {
                if (TRACE) TRACE_PRINT("DONE at cycle %s scheduleCycle %s\n", ctx.cycle, scheduleCycle);
                break;
            }

            if (tpl.has_dst) {
                RegInfo ri = ctx.registers[instr.dst];
                int retireCycle = scheduleCycle + tpl.latency;
                ri.latency = retireCycle;
                ri.last_op = tpl.group;
                ri.last_op_par = instr.op_par;
                ctx.latency = Math.max(retireCycle, ctx.latency);
                if (TRACE) TRACE_PRINT("; RETIRED at cycle %s\n", retireCycle);
            }

            program.code_size++;
            if (is_mul(instr.opcode))
                ctx.mul_count++;
            ++ctx.sub_cycle;
            if (tpl.uop2 != PORT_NONE)
                ctx.sub_cycle++;
            ctx.cycle = ctx.sub_cycle / 3;
        }

        /* reject programs that don't meet the uniform complexity requirements */
        /* this happens in less than 1 seed out of 10000 */

        boolean rv =
            (program.code_size == REQUIREMENT_SIZE) &&
            (ctx.mul_count == REQUIREMENT_MUL_COUNT) &&
            (ctx.latency == REQUIREMENT_LATENCY - 1); /* cycles are numbered from 0 */
        if (!rv) {
            System.out.println("Program size: " + program.code_size + " expected: " + REQUIREMENT_SIZE);
            System.out.println("Mul count:    " + ctx.mul_count + " expected: " + REQUIREMENT_MUL_COUNT);
            System.out.println("Latency:      " + ctx.latency + " expected: " + (REQUIREMENT_LATENCY - 1));
        }
        return rv;
    }

    private static void printf(String s, int i) {
        System.out.print(String.format(s, i));
    }

    private static void printf(String s, int i, int j) {
        System.out.print(String.format(s, i, j));
    }

    private static void TRACE_PRINT(String s, int i) {
        System.out.print(String.format(s, i));
    }

    private static void TRACE_PRINT(String s, int i, int j) {
        System.out.print(String.format(s, i, j));
    }

    private static void TRACE_PRINT(String s, Object i) {
        System.out.print(String.format(s, i));
    }

    private static void TRACE_PRINT(String s, Object i, Object j) {
        System.out.print(String.format(s, i, j));
    }

    private static void TRACE_PRINT(String s, Object i, Object j, Object k) {
        System.out.print(String.format(s, i, j, k));
    }

}
