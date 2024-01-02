package net.i2p.pow.equix;

import static net.i2p.pow.equix.Heap.*;
import static net.i2p.pow.equix.Util.*;
import net.i2p.pow.hashx.HashX;
import net.i2p.pow.hashx.HXCtx;

/**
 *  Solver
 */
class Solver {

    private static void build_solution_stage1(char[] output, Heap heap, int root) {
        int bucket = ITEM_BUCKET(root);
        int bucket_inv = INVERT_BUCKET(bucket);
        int left_parent_idx = ITEM_LEFT_IDX(root);
        int right_parent_idx = ITEM_RIGHT_IDX(root);
        char left_parent = heap.STAGE1_IDX(bucket, left_parent_idx);
        char right_parent = heap.STAGE1_IDX(bucket_inv, right_parent_idx);
        //output[0] = left_parent;
        //output[1] = right_parent;
        //if (!tree_cmp1(output[0], output[1])) {
        //    SWAP_IDX(output, 0, 1);
        //}
        if (left_parent <= right_parent) {
            output[0] = left_parent;
            output[1] = right_parent;
        } else {
            output[0] = right_parent;
            output[1] = left_parent;
        }
    }

    private static void build_solution_stage2(char[] output, Heap heap, int root) {
        int bucket = ITEM_BUCKET(root);
        int bucket_inv = INVERT_BUCKET(bucket);
        int left_parent_idx = ITEM_LEFT_IDX(root);
        int right_parent_idx = ITEM_RIGHT_IDX(root);
        int left_parent = heap.STAGE2_IDX(bucket, left_parent_idx);
        int right_parent = heap.STAGE2_IDX(bucket_inv, right_parent_idx);
        build_solution_stage1(output, heap, left_parent);
        char[] tmp = new char[2];
        build_solution_stage1(tmp, heap, right_parent);
        output[2] = tmp[0];
        output[3] = tmp[1];
        if (!tree_cmp2(output, 0, 2)) {
            SWAP_IDX(output, 0, 2);
            SWAP_IDX(output, 1, 3);
        }
    }

    private static void build_solution(char[] solution, Heap heap, int left, int right) {
        build_solution_stage2(solution, heap, left);
        char[] tmp = new char[4];
        build_solution_stage2(tmp, heap, right);
        solution[4] = tmp[0];
        solution[5] = tmp[1];
        solution[6] = tmp[2];
        solution[7] = tmp[3];
        if (!tree_cmp4(solution, 0, 4)) {
            SWAP_IDX(solution, 0, 4);
            SWAP_IDX(solution, 1, 5);
            SWAP_IDX(solution, 2, 6);
            SWAP_IDX(solution, 3, 7);
        }
    }

    /**
     *  Calculate and store 64K HashX hashes in stage1 data
     */
    private static int stage0(HXCtx ctx, Heap heap) {
        heap.CLEAR_STAGE1();
        byte[] hash = new byte[Equix.HASHX_SIZE];
        for (int i = 0; i < INDEX_SPACE; ++i) {
            //long value = hash_value(ctx, i);
            HashX.exec(ctx, i, hash);
            long value = HashX.fromLong8LE(hash, 0);
            int bucket_idx = (int) (value & NUM_COARSE_BUCKETS_MASK);
            int item_idx = heap.STAGE1_SIZE(bucket_idx);
            if (item_idx >= COARSE_BUCKET_ITEMS)
                continue;
            heap.SET_STAGE1_SIZE(bucket_idx, item_idx + 1);
            heap.SET_STAGE1_IDX(bucket_idx, item_idx, i);
            // 52 bits = 13 bytes
            value >>= NUM_COARSE_BUCKETS_BITS;
            value &= 0xfffffffffffffL;
            heap.SET_STAGE1_DATA(bucket_idx, item_idx, value);
        }
        return 0;
    }

    /**
     *  Go thru the stage1 data and store stage2 data
     */
    private static void stage1(Heap heap) {
        heap.CLEAR_STAGE2();
        for (int bucket_idx = BUCK_START; bucket_idx < BUCK_END; ++bucket_idx) {
            int cpl_bucket = INVERT_BUCKET(bucket_idx);
            heap.CLEAR_SCRATCH();
            int cpl_buck_size = heap.STAGE1_SIZE(cpl_bucket);
            for (int item_idx = 0; item_idx < cpl_buck_size; ++item_idx) {
                long value = heap.STAGE1_DATA(cpl_bucket, item_idx);
                int fine_buck_idx = (int) (value & NUM_FINE_BUCKETS_MASK);
                int fine_item_idx = heap.SCRATCH_SIZE(fine_buck_idx);
                if (fine_item_idx >= FINE_BUCKET_ITEMS)
                    continue;
                heap.SET_SCRATCH_SIZE(fine_buck_idx, fine_item_idx + 1);
                heap.SET_SCRATCH(fine_buck_idx, fine_item_idx, item_idx);
                if (cpl_bucket == bucket_idx) {
                    heap.MAKE_PAIRS1(bucket_idx, item_idx, cpl_bucket);
                }
            }
            if (cpl_bucket != bucket_idx) {
                int buck_size = heap.STAGE1_SIZE(bucket_idx);
                for (int item_idx = 0; item_idx < buck_size; ++item_idx) {
                    heap.MAKE_PAIRS1(bucket_idx, item_idx, cpl_bucket);
                }
            }
        }
    }

    /**
     *  Go thru the stage2 data and store stage3 data
     */
    private static void stage2(Heap heap) {
        heap.CLEAR_STAGE3();
        for (int bucket_idx = BUCK_START; bucket_idx < BUCK_END; ++bucket_idx) {
            int cpl_bucket = INVERT_BUCKET(bucket_idx);
            heap.CLEAR_SCRATCH();
            int cpl_buck_size = heap.STAGE2_SIZE(cpl_bucket);
            for (int item_idx = 0; item_idx < cpl_buck_size; ++item_idx) {
                long value = heap.STAGE2_DATA(cpl_bucket, item_idx);
                int fine_buck_idx = (int) (value & NUM_FINE_BUCKETS_MASK);
                int fine_item_idx = heap.SCRATCH_SIZE(fine_buck_idx);
                if (fine_item_idx >= FINE_BUCKET_ITEMS)
                    continue;
                heap.SET_SCRATCH_SIZE(fine_buck_idx, fine_item_idx + 1);
                heap.SET_SCRATCH(fine_buck_idx, fine_item_idx, item_idx);
                if (cpl_bucket == bucket_idx) {
                    heap.MAKE_PAIRS2(bucket_idx, item_idx, cpl_bucket);
                }
            }
            if (cpl_bucket != bucket_idx) {
                int buck_size = heap.STAGE2_SIZE(bucket_idx);
                for (int item_idx = 0; item_idx < buck_size; ++item_idx) {
                    heap.MAKE_PAIRS2(bucket_idx, item_idx, cpl_bucket);
                }
            }
        }
    }

    private static int MAKE_PAIRS3(Heap heap, int sols_found, char[][] output, int bucket_idx, int item_idx, int cpl_bucket) {
        int value = heap.STAGE3_DATA(bucket_idx, item_idx);
        if (bucket_idx != 0) // CARRY
            value++;
        int fine_buck_idx = value & NUM_FINE_BUCKETS_MASK;
        int fine_cpl_bucket = INVERT_SCRATCH(fine_buck_idx);
        int fine_cpl_size = heap.SCRATCH_SIZE(fine_cpl_bucket);
        for (int fine_idx = 0; fine_idx < fine_cpl_size; ++fine_idx) {
            int cpl_index = heap.SCRATCH(fine_cpl_bucket, fine_idx);
            int cpl_value = heap.STAGE3_DATA(cpl_bucket, cpl_index);
            int sum = value + cpl_value;
            assert((sum & NUM_FINE_BUCKETS_MASK) == 0);
            // 15 bits
            sum >>= NUM_FINE_BUCKETS_BITS;
            if ((sum & Equix.EQUIX_STAGE1_MASK) == 0) {
                /* we have a solution */
                int item_left = heap.STAGE3_IDX(bucket_idx, item_idx);
                int item_right = heap.STAGE3_IDX(cpl_bucket, cpl_index);
                build_solution(output[sols_found], heap, item_left, item_right);
                System.out.println("Found solution " + sols_found + " left " + item_left + " right " + item_right);
                if (++sols_found >= Equix.EQUIX_MAX_SOLS)
                    return sols_found;
            }
        }
        return sols_found;
    }

    /**
     *  Go thru the stage3 data and find solutions
     *
     *  @return solutions found and added to output
     */
    private static int stage3(Heap heap, char[][] output, int sols_found) {
        for (int bucket_idx = BUCK_START; bucket_idx < BUCK_END; ++bucket_idx) {
            int cpl_bucket = INVERT_BUCKET(bucket_idx);
            // unused in C code
            //boolean nodup = cpl_bucket == bucket_idx;
            heap.CLEAR_SCRATCH();
            int cpl_buck_size = heap.STAGE3_SIZE(cpl_bucket);
            for (int item_idx = 0; item_idx < cpl_buck_size; ++item_idx) {
                int value = heap.STAGE3_DATA(cpl_bucket, item_idx);
                int fine_buck_idx = value & NUM_FINE_BUCKETS_MASK;
                int fine_item_idx = heap.SCRATCH_SIZE(fine_buck_idx);
                if (fine_item_idx >= FINE_BUCKET_ITEMS)
                    continue;
                heap.SET_SCRATCH_SIZE(fine_buck_idx, fine_item_idx + 1);
                heap.SET_SCRATCH(fine_buck_idx, fine_item_idx, item_idx);
                if (cpl_bucket == bucket_idx) {
                    sols_found = MAKE_PAIRS3(heap, sols_found, output, bucket_idx, item_idx, cpl_bucket);
                    if (sols_found >= Equix.EQUIX_MAX_SOLS)
                        return sols_found;
                }
            }
            if (cpl_bucket != bucket_idx) {
                int buck_size = heap.STAGE3_SIZE(bucket_idx);
                for (int item_idx = 0; item_idx < buck_size; ++item_idx) {
                    sols_found = MAKE_PAIRS3(heap, sols_found, output, bucket_idx, item_idx, cpl_bucket);
                    if (sols_found >= Equix.EQUIX_MAX_SOLS)
                        return sols_found;
                }
            }
        }
        return sols_found;
    }

    public static int solve(HXCtx ctx, Heap heap, char[][] solutions, int solution_count) {
        long start = System.currentTimeMillis();
        stage0(ctx, heap);
        long now = System.currentTimeMillis();
        System.out.println("stage0() took " + (now - start));
        start = now;

        stage1(heap);
        now = System.currentTimeMillis();
        System.out.println("stage1() took " + (now - start));
        start = now;

        stage2(heap);
        now = System.currentTimeMillis();
        System.out.println("stage2() took " + (now - start));
        start = now;

        int rv = stage3(heap, solutions, solution_count);
        now = System.currentTimeMillis();
        System.out.println("stage3() took " + (now - start));
        return rv;
    }
}
