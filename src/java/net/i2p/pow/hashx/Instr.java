package net.i2p.pow.hashx;

class Instr {

    IType opcode;
    int src, dst, imm32;
    int op_par;

    @Override
    public String toString() {
        return "(" + opcode.ordinal() + ") " + opcode +
               (src >= 0 ? (" r" + src + "->") : "") +
               (dst >= 0 ? (" r" + dst) : "") +
               (src < 0 || dst < 0 ? (" imm " + imm32) : "") +
               (op_par != -1 ? (" op_par " + op_par) : "");
    }
}
