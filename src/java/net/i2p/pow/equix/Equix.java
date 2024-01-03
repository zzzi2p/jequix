package net.i2p.pow.equix;

import net.i2p.data.DataHelper;

import static net.i2p.pow.equix.Result.*;
import static net.i2p.pow.equix.Util.*;
import net.i2p.pow.hashx.HashX;
import net.i2p.pow.hashx.HXCtx;

/**
 *  Entry point
 */
public class Equix {
    public static final int HASHX_SIZE = 16;
    public static final int EQUIX_MAX_SOLS = 8;
    public static final int EQUIX_NUM_IDX = 8;
    static final long EQUIX_STAGE1_MASK = ((1L << 15) - 1);
    private static final long EQUIX_STAGE2_MASK = ((1L << 30) - 1);
    private static final long EQUIX_FULL_MASK   = ((1L << 60) - 1);
    public static final int REQUIREMENT_SIZE = 512;

    private static boolean verify_order(char[] solution) {
        return
                tree_cmp4(solution, 0, 4) &&
                tree_cmp2(solution, 0, 2) &&
                tree_cmp2(solution, 4, 6) &&
                solution[0] <= solution[1] &&
                solution[2] <= solution[3] &&
                solution[4] <= solution[5] &&
                solution[6] <= solution[7];
    }

    private static long sum_pair(HXCtx ctx, char left, char right) {
        byte[] hash_left = new byte[HASHX_SIZE];
        byte[] hash_right = new byte[HASHX_SIZE];
        HashX.exec(ctx, left, hash_left);
        HashX.exec(ctx, right, hash_right);
        return HashX.fromLong8LE(hash_left, 0) + HashX.fromLong8LE(hash_right, 0);
    }

    private static Result verify_internal(HXCtx ctx, char[] solution) {
        long pair0 = sum_pair(ctx, solution[0], solution[1]);
        if ((pair0 & EQUIX_STAGE1_MASK) != 0)
            return PARTIAL_SUM;
        long pair1 = sum_pair(ctx, solution[2], solution[3]);
        if ((pair1 & EQUIX_STAGE1_MASK) != 0)
            return PARTIAL_SUM;
        long pair4 = pair0 + pair1;
        if ((pair4 & EQUIX_STAGE2_MASK) != 0)
            return PARTIAL_SUM;
        long pair2 = sum_pair(ctx, solution[4], solution[5]);
        if ((pair2 & EQUIX_STAGE1_MASK) != 0)
            return PARTIAL_SUM;
        long pair3 = sum_pair(ctx, solution[6], solution[7]);
        if ((pair3 & EQUIX_STAGE1_MASK) != 0)
            return PARTIAL_SUM;
        long pair5 = pair2 + pair3;
        if ((pair5 & EQUIX_STAGE2_MASK) != 0)
            return PARTIAL_SUM;
        long pair6 = pair4 + pair5;
        if ((pair6 & EQUIX_FULL_MASK) != 0)
            return FINAL_SUM;
        return OK;
    }

    /**
     *  @param output [8][8]
     *  @return number of solutions in output (0 to EQUIX_MAX_SOLS)
     */
    public static int solve(HXCtx ctx, Heap heap, byte[] challenge, int csz, char[][] solutions, int solution_count) {
        if (ctx.code_size == 0 || !DataHelper.eq(challenge, 0, ctx.seed, 0, csz)) {
            if (!HashX.make(ctx, challenge, csz)) {
                System.out.println("FAILED to generate HashX for solving");
                return 0;
            }
        } else if (ctx.code_size != REQUIREMENT_SIZE) {
            throw new IllegalArgumentException("code size is " + ctx.code_size);
        }
        return Solver.solve(ctx, heap, solutions, solution_count);
    }

    public static Result verify(HXCtx ctx, byte[] challenge, int csz, char[] solution) {
        if (!verify_order(solution)) {
/*
            char[] s = solution;
            System.out.print("s0/1 " + (int) s[0] + " <= " + (int) s[1]);
            System.out.println(s[0] <= s[1] ? " PASS" : " FAIL");
            System.out.print("s2/3 " + (int) s[2] + " <= " + (int) s[3]);
            System.out.println(s[2] <= s[3] ? " PASS" : " FAIL");
            System.out.print("s4/5 " + (int) s[4] + " <= " + (int) s[5]);
            System.out.println(s[4] <= s[5] ? " PASS" : " FAIL");
            System.out.print("s6/7 " + (int) s[6] + " <= " + (int) s[7]);
            System.out.println(s[6] <= s[7] ? " PASS" : " FAIL");
            long l = (((long) s[0]) << 16) | s[1];
            long r = (((long) s[2]) << 16) | s[3];
            System.out.print("s01/23 " + l + " <= " + r);
            System.out.println(l <= r ? " PASS" : " FAIL");
            l = (((long) s[4]) << 16) | s[5];
            r = (((long) s[6]) << 16) | s[7];
            System.out.print("s45/67 " + l + " <= " + r);
            System.out.println(l <= r ? " PASS" : " FAIL");
            l = (((long) s[0]) << 48) | (((long) s[1]) << 32) | (((long) s[2]) << 16) | s[3];
            r = (((long) s[4]) << 48) | (((long) s[5]) << 32) | (((long) s[6]) << 16) | s[7];
            System.out.print("s0123/4567 0x" + Long.toString(l, 16) + " <= 0x" + Long.toString(r, 16));
            System.out.println(Long.compareUnsigned(l, r) <= 0 ? " PASS" : " FAIL");
*/
            return ORDER;
        }
        if (ctx.code_size == 0 || !DataHelper.eq(challenge, 0, ctx.seed, 0, csz)) {
            ctx.compiled = false;
            ctx.compiled_method = null;
            if (!HashX.make(ctx, challenge, csz)) {
                System.out.println("FAILED to generate HashX for verifying");
                return CHALLENGE;
            }
        } else if (ctx.code_size != REQUIREMENT_SIZE) {
            throw new IllegalArgumentException("code size is " + ctx.code_size);
        }
        return verify_internal(ctx, solution);
    }
}
