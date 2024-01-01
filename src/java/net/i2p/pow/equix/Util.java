package net.i2p.pow.equix;

/**
 *  Utilities
 */
class Util {

    /**
     * @param left value
     * @param right value
     */
/*
    static boolean tree_cmp1(char left, char right) {
        return left <= right;
    }
*/

    /**
     * @param left index
     * @param right index
     */
    static boolean tree_cmp2(char[] s, int left, int right) {
        long l = (((long) s[left]) << 16) | s[left + 1];
        long r = (((long) s[right]) << 16) | s[right + 1];
        return l <= r;
    }

    /**
     * @param left index
     * @param right index
     */
    static boolean tree_cmp4(char[] s, int left, int right) {
        long l = (((long) s[left]) << 48) | (((long) s[left + 1]) << 32) | (((long) s[left + 2]) << 16) | s[left + 3];
        long r = (((long) s[right]) << 48) | (((long) s[right + 1]) << 32) | (((long) s[right + 2]) << 16) | s[right + 3];
        return Long.compareUnsigned(l, r) <= 0;
    }

    static void SWAP_IDX(char[] s, int a, int b) {
        char tmp = s[a];
        s[a] = s[b];
        s[b] = tmp;
    }
}
