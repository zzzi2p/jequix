package net.i2p.pow.equix;

import java.util.Arrays;

import net.i2p.data.DataHelper;

import static net.i2p.pow.equix.Util.SWAP_IDX;
import net.i2p.pow.hashx.HXCtx;

/**
 *  Tests
 */
public class Test {
    private static HXCtx ctx = new HXCtx(512);
    private static Heap heap = new Heap();
    private static char[][] solutions = new char[Equix.EQUIX_MAX_SOLS][8];
    private static int nonce;
    private static byte[] bnonce = new byte[4];
    private static int valid_count;
    private static int test_no;
    private static int num_solutions;

    private static void run_test(boolean ok) {
        System.out.println("Test " + (++test_no) + (ok ? " PASSED" : " SKIPPED"));
    }

    private static boolean test_solve() {
        for (nonce = 0; num_solutions == 0 && nonce < 20; ++nonce) {
            DataHelper.toLong(bnonce, 0, 4, nonce);
            System.out.println("Calling solver with nonce " + nonce);
            num_solutions = Equix.solve(ctx, heap, bnonce, 4, solutions, num_solutions);
        }
        --nonce;
        assert(num_solutions > 0);
        System.out.println("Found " + num_solutions + " solutions with nonce " + nonce);
        return true;
    }

    private static boolean test_verify1() {
        System.out.println("Verifying " + num_solutions + " solutions with nonce " + nonce);
        DataHelper.toLong(bnonce, 0, 4, nonce);
        for (int i = 0; i < num_solutions; i++) {
            System.out.println("Verifying solution " + i + ": " +
                               (int) solutions[i][0] + ' ' +
                               (int) solutions[i][1] + ' ' +
                               (int) solutions[i][2] + ' ' +
                               (int) solutions[i][3] + ' ' +
                               (int) solutions[i][4] + ' ' +
                               (int) solutions[i][5] + ' ' +
                               (int) solutions[i][6] + ' ' +
                               (int) solutions[i][7]);
            Result result = Equix.verify(ctx, bnonce, 4, solutions[i]);
            assert(result == Result.OK);
        }
        return true;
    }

    private static boolean test_verify2() {
        System.out.println("Testing first solution order 1 with nonce " + nonce);
        char[] solution = solutions[0];
        SWAP_IDX(solution, 0, 1);
        DataHelper.toLong(bnonce, 0, 4, nonce);
        Result result = Equix.verify(ctx, bnonce, 4, solution);
        System.out.println("Result is " + result);
        assert(result == Result.ORDER);
        // swap back not in C code, breaks test_verify4() if we don't
        SWAP_IDX(solution, 0, 1);
        return true;
    }

    private static boolean test_verify3() {
        System.out.println("Testing first solution order 4 with nonce " + nonce);
        char[] solution = solutions[0];
        SWAP_IDX(solution, 0, 4);
        SWAP_IDX(solution, 1, 5);
        SWAP_IDX(solution, 2, 6);
        SWAP_IDX(solution, 3, 7);
        DataHelper.toLong(bnonce, 0, 4, nonce);
        Result result = Equix.verify(ctx, bnonce, 4, solution);
        System.out.println("Result is " + result);
        assert(result == Result.ORDER);
        SWAP_IDX(solution, 0, 4);
        SWAP_IDX(solution, 1, 5);
        SWAP_IDX(solution, 2, 6);
        SWAP_IDX(solution, 3, 7);
        return true;
    }

    private static boolean test_verify4() {
        System.out.println("Testing first solution partial with nonce " + nonce);
        char[] solution = solutions[0];
        SWAP_IDX(solution, 1, 2);
        DataHelper.toLong(bnonce, 0, 4, nonce);
        Result result = Equix.verify(ctx, bnonce, 4, solution);
        System.out.println("Result is " + result);
        assert(result == Result.PARTIAL_SUM);
        SWAP_IDX(solution, 1, 2);
        return true;
    }

    private static void permute_idx(int start) {
        char[] solution = solutions[0];
        if (start == Equix.EQUIX_NUM_IDX - 1) {
            DataHelper.toLong(bnonce, 0, 4, nonce);
            Result result = Equix.verify(ctx, bnonce, 4, solution);
            if (result == Result.OK)
                 valid_count ++;
        } else {
            for (int i = start; i < Equix.EQUIX_NUM_IDX; ++i)    {
                SWAP_IDX(solution, start, i);
                permute_idx(start + 1);
                SWAP_IDX(solution, start, i);
            }
        }
    }

    private static boolean test_permutations() {
        System.out.println("Testing first solution permutations with nonce " + nonce);
        permute_idx(0);
        assert(valid_count == 1); /* check that only one of the 40320 possible
                                     permutations of indices is a valid solution */
        return true;
    }

    public static void main(String[] args) {
        run_test(test_solve());
        run_test(test_verify1());
        run_test(test_verify2());
        run_test(test_verify3());
        run_test(test_verify4());
        run_test(test_permutations());
        System.out.println("\nAll tests were successful\n");
    }
}
