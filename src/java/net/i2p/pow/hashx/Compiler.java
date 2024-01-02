package net.i2p.pow.hashx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

class Compiler {

    static boolean compile(HXCtx ctx, long input) throws IOException {
        PrintWriter out = null;
        long[] r = new long[8];
        SipHash.ctr_state512(ctx.keys, input, r);
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Compiled.java"), "UTF-8"));
            out.println("package net.i2p.pow.hashx;");
            out.println("class Compiled {");
            out.println("static void execute(long[] r) {");
            out.println("long r0 = r[0];");
            out.println("long r1 = r[1];");
            out.println("long r2 = r[2];");
            out.println("long r3 = r[3];");
            out.println("long r4 = r[4];");
            out.println("long r5 = r[5];");
            out.println("long r6 = r[6];");
            out.println("long r7 = r[7];");
            Instr[] program = ctx.code;
            generate(program, out);
            out.println("r[0] = r0;");
            out.println("r[1] = r1;");
            out.println("r[2] = r2;");
            out.println("r[3] = r3;");
            out.println("r[4] = r4;");
            out.println("r[5] = r5;");
            out.println("r[6] = r6;");
            out.println("r[7] = r7;");
            out.println("}");
            out.println("}");
        } finally {
            if (out != null) out.close();
        }
        return true;
    }

    static void generate(Instr[] program, PrintWriter out) throws IOException {
        //out.println("int target = 0;");
        out.println("boolean branch_enable = true;");
        out.println("int result = 0;");
        //out.println("int branch_idx = 0;");
        boolean has_target = false;
        for (int i = 0; i < program.length; i++) {
            Instr instr = program[i];
            out.println("// " + i + ": " + instr);
            //out.println("System.out.println(\"// inst " + i + " \" + r0 + ' ' + r1 + ' ' + r2 + ' ' + r3 + ' ' + r4 + ' ' + r5 + ' ' + r6 + ' ' + r7);");
            switch (instr.opcode) {
                case INSTR_UMULH_R:
                    out.println("r" + instr.dst + " = Exec.umulh(r" + instr.dst + ", r" + instr.src + ");");
                    out.println("result = (int) r" + instr.dst + ";");
                    break;

                case INSTR_SMULH_R:
                    out.println("r" + instr.dst + " = Exec.smulh(r" + instr.dst + ", r" + instr.src + ");");
                    out.println("result = (int) r" + instr.dst + ";");
                    break;

                case INSTR_MUL_R:
                    out.println("r" + instr.dst + " *= r" + instr.src + ";");
                    break;

                case INSTR_SUB_R:
                    out.println("r" + instr.dst + " -= r" + instr.src + ";");
                    break;

                case INSTR_XOR_R:
                    out.println("r" + instr.dst + " ^= r" + instr.src + ";");
                    break;

                case INSTR_ADD_RS:
                    out.println("r" + instr.dst + " += r" + instr.src + " << " + instr.imm32 + ";");
                    break;

                case INSTR_ROR_C:
                    out.println("r" + instr.dst + " = Long.rotateRight(r" + instr.dst + ", " + instr.imm32 + ");");
                    break;

                case INSTR_ADD_C:
                    out.println("r" + instr.dst + " += " + instr.imm32 + ";");
                    break;

                case INSTR_XOR_C:
                    out.println("r" + instr.dst + " ^= " + instr.imm32 + ";");
                    break;

                case INSTR_TARGET:
                    // unused
                    //out.println("target = " + i + ";");
                    if (has_target) {
                        // there may be multiple targets before the first branch
                        out.println("// no branch to previous target");
                        //out.println("System.out.println(\"// no branch to previous target\");");
                        out.println("break;");
                        out.println("} // for loop");
                    } else {
                        has_target = true;
                    }
                    //out.println("System.out.println(\"// branch target " + i + "\");");
                    out.println("// branch target " + i);
                    out.println("for (int x = 0; x < 2; x++) {");
                    break;

                case INSTR_BRANCH:
                    if (has_target) {
                        out.println("if (x == 0 && branch_enable && ((result & " + instr.imm32 + ") == 0)) {");
                        //out.println("    i = target;");
                        //out.println("System.out.println(\"// branch to target from " + i + "\");");
                        out.println("// branch to target from " + i);
                        out.println("    branch_enable = false;");
                        out.println("} else {");
                        //out.println("System.out.println(\"// branch to target not taken at " + i + "\");");
                        out.println("    break;");
                        out.println("}");
                        out.println("} // for loop");
                        //out.println("branch_idx++;");
                        has_target = false;
                    } else {
                        //out.println("System.out.println(\"// branch without target at " + i + "\");");
                        out.println("// branch without target at " + i);
                    }
                    break;

                default:
                    break;
            }
        }
        // target but no branch?
        if (has_target) {
            out.println("// EOF no branch instruction found to target");
            out.println("} // for loop");
        }
    }
}
