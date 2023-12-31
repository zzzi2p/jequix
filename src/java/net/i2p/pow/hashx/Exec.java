package net.i2p.pow.hashx;

class Exec {

    private static long LO(long x) { return x & 0xffffffffL; }
    private static long HI(long x) { return (x >> 32) & 0xffffffffL; }

    private static long umulh(long a, long b) {
        long ah = HI(a), al = LO(a);
        long bh = HI(b), bl = LO(b);
        long x00 = al * bl;
        long x01 = al * bh;
        long x10 = ah * bl;
        long x11 = ah * bh;
        long m1 = LO(x10) + LO(x01) + HI(x00);
        long m2 = HI(x10) + HI(x01) + LO(x11) + HI(m1);
        long m3 = HI(x11) + HI(m2);

        return (m3 << 32) + LO(m2);
    }

    private static long smulh(long a, long b) {
        long hi = umulh(a, b);
        if (a < 0) hi -= b;
        if (b < 0) hi -= a;
        return hi;
    }

    static void execute(Instr[] program, long[] r) {
        int target = 0;
        boolean branch_enable = true;
        int result = 0;
        int branch_idx = 0;
        for (int i = 0; i < program.length; i++) {
            Instr instr = program[i];
            switch (instr.opcode) {
                case INSTR_UMULH_R:
                    r[instr.dst] = umulh(r[instr.dst], r[instr.src]);
                    result = (int) r[instr.dst];
                    break;

                case INSTR_SMULH_R:
                    r[instr.dst] = smulh(r[instr.dst], r[instr.src]);
                    result = (int) r[instr.dst];
                    break;

                case INSTR_MUL_R:
                    r[instr.dst] *= r[instr.src];
                    break;

                case INSTR_SUB_R:
                    r[instr.dst] -= r[instr.src];
                    break;

                case INSTR_XOR_R:
                    r[instr.dst] ^= r[instr.src];
                    break;

                case INSTR_ADD_RS:
                    r[instr.dst] += r[instr.src] << instr.imm32;
                   break;

                case INSTR_ROR_C:
                    r[instr.dst] = Long.rotateRight(r[instr.dst], instr.imm32);
                    break;

                case INSTR_ADD_C:
                    r[instr.dst] += instr.imm32;
                    break;

                case INSTR_XOR_C:
                    r[instr.dst] ^= instr.imm32;
                    break;

                case INSTR_TARGET:
                    target = i;
                    break;

                case INSTR_BRANCH:
                    if (branch_enable && (result & instr.imm32) == 0) {
                        i = target;
                        branch_enable = false;
                    }
                    branch_idx++;
                    break;

                default:
                    break;
            }
        }
    }
}
