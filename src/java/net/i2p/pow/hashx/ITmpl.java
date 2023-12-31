package net.i2p.pow.hashx;

import static net.i2p.pow.hashx.IType.*;
import static net.i2p.pow.hashx.EPort.*;

/**
 *  Instruction Templates
 */
enum ITmpl {
    TMPL_UMULH_R(INSTR_UMULH_R, 4, PORT_P1,   PORT_P5,   0,    false, false, false, true,  true),
    TMPL_SMULH_R(INSTR_SMULH_R, 4, PORT_P1,   PORT_P5,   0,    false, false, false, true,  true),
    TMPL_MUL_R  (INSTR_MUL_R,   3, PORT_P1,   PORT_NONE, 0,    false, true,  true,  true,  true),
    TMPL_SUB_R  (INSTR_SUB_R,   1, PORT_P015, PORT_NONE, 0,    false, true,  true,  true,  true),
    TMPL_XOR_R  (INSTR_XOR_R,   1, PORT_P015, PORT_NONE, 0,    false, true,  true,  true,  true),
    TMPL_ADD_RS (INSTR_ADD_RS,  1, PORT_P01,  PORT_NONE, 3,    true,  true,  true,  true,  true),
    TMPL_ROR_C  (INSTR_ROR_C,   1, PORT_P05,  PORT_NONE, 63,   false, true,  false, false, true),
    TMPL_ADD_C  (INSTR_ADD_C,   1, PORT_P015, PORT_NONE, -1,   false, true,  false, false, true),
    TMPL_XOR_C  (INSTR_XOR_C,   1, PORT_P015, PORT_NONE, -1,   false, true,  false, false, true),
    TMPL_TARGET (INSTR_TARGET,  1, PORT_P015, PORT_P015, 0,    false, true,  false, false, false),
    TMPL_BRANCH (INSTR_BRANCH,  1, PORT_P015, PORT_P015, Program.BRANCH_MASK,
                                                               false, true,  false, false, false);

    final IType type, group;
    final int latency;
    final EPort uop1, uop2;
    final int imm_mask;
    final boolean imm_0, distinct_dst, op_par_src, has_src, has_dst;
    final String x86_asm = "";

    private ITmpl(IType type, int l, EPort p1, EPort p2, int imm_mask, boolean imm_0, boolean distinct_dest, boolean par_src, boolean has_src, boolean has_dest) {
        this.type = type;
        latency = l;
        // only one case where group != type
        group = (type == INSTR_SUB_R) ? INSTR_ADD_RS : type;
        uop1 = p1;
        uop2 = p2;
        this.imm_mask = imm_mask;
        this.imm_0 = imm_0;
        distinct_dst = distinct_dest;
        op_par_src = par_src;
        this.has_src = has_src;
        has_dst = has_dest;
    }
}
