package net.i2p.pow.hashx;

/**
 *  Instruction Type (opcode)
 */
enum IType {
    INSTR_UMULH_R,
    INSTR_SMULH_R,
    INSTR_MUL_R,
    INSTR_SUB_R,
    INSTR_XOR_R,
    INSTR_ADD_RS,
    INSTR_ROR_C,
    INSTR_ADD_C,
    INSTR_XOR_C,
    INSTR_TARGET,
    INSTR_BRANCH
}
