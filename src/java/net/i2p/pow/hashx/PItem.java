package net.i2p.pow.hashx;

import static net.i2p.pow.hashx.ITmpl.*;

/**
 *  Program Item
 */
enum PItem {

    ITEM_MUL(Templates.ARR_MUL_R, 0, 0, true),
    ITEM_TARGET(Templates.ARR_TARGET, 0, 0, true),
    ITEM_BRANCH(Templates.ARR_BRANCH, 0, 0, true),
    ITEM_WIDE_MUL(Templates.ARR_WIDE_MUL, 1, 1, true),
    ITEM_ANY(Templates.ARR_LOOKUP, 7, 3, false);

    final ITmpl[] templates;
    final int mask0, mask1;
    final boolean dups;

    private PItem(ITmpl[] tmpl, int mask0, int mask1, boolean dups) {
        templates = tmpl;
        this.mask0 = mask0;
        this.mask1 = mask1;
        this.dups = dups;
    }

    private static class Templates {
        private static final ITmpl[] ARR_MUL_R =    { TMPL_MUL_R };
        private static final ITmpl[] ARR_TARGET =   { TMPL_TARGET };
        private static final ITmpl[] ARR_BRANCH =   { TMPL_BRANCH };
        private static final ITmpl[] ARR_WIDE_MUL = { TMPL_SMULH_R, TMPL_UMULH_R };
        private static final ITmpl[] ARR_LOOKUP =   { TMPL_ROR_C, TMPL_XOR_C, TMPL_ADD_C, TMPL_ADD_C, TMPL_SUB_R, TMPL_XOR_R, TMPL_XOR_C, TMPL_ADD_RS };
    }
}
