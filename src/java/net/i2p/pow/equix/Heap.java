package net.i2p.pow.equix;

import java.util.Arrays;

/**
 *  Solver Heap
 */
public class Heap {
    static final int INDEX_SPACE = 1 << 16;
    static final int NUM_COARSE_BUCKETS = 256;
    static final int NUM_FINE_BUCKETS = 128;
    // to avoid negative values with %
    static final int NUM_COARSE_BUCKETS_MASK = NUM_COARSE_BUCKETS - 1;
    static final int NUM_FINE_BUCKETS_MASK = NUM_FINE_BUCKETS - 1;
    // shift amounts
    static final int NUM_COARSE_BUCKETS_BITS = 8;
    static final int NUM_FINE_BUCKETS_BITS = 7;
    static final int COARSE_BUCKET_ITEMS = 336;
    static final int FINE_BUCKET_ITEMS = 12;
    static final int BUCK_START = 0;
    static final int BUCK_END = (NUM_COARSE_BUCKETS / 2) + 1;

    //stage1_idx_hashtab stage1_indices;           /* 172 544 bytes */
    // 256 * 2 = 512
    private final char[] stage1_counts = new char[NUM_COARSE_BUCKETS];
    // 256 * 336 * 2 = 172032
    private final char[][] stage1_buckets = new char[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];

    //stage2_idx_hashtab stage2_indices;           /* 344 576 bytes */
    // 256 * 2 = 512
    private final char[] stage2_counts = new char[NUM_COARSE_BUCKETS];
    // 256 * 336 * 4 = 344064
    private final int[][] stage2_buckets = new int[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];

    //stage2_data_hashtab stage2_data;             /* 688 128 bytes */
    // 256 * 336 * 8 = 688128
    private final long[][] stage2_data_buckets = new long[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];

    //union {
        //stage1_data_hashtab stage1_data;         /* 688 128 bytes */
        private final long[][] stage1_data_buckets = new long[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];

        //struct {
            //stage3_idx_hashtab stage3_indices;   /* 344 576 bytes */
            // 256 * 2 = 512
            private final char[] stage3_counts = new char[NUM_COARSE_BUCKETS];
            // TODO we can use nio Buffers to overlap the long and int arrays
            // 256 * 336 * 4 = 344064
            private final int[][] stage3_buckets = new int[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];
            //stage3_data_hashtab stage3_data;     /* 344 064 bytes */
            private final int[][] stage3_data_buckets = new int[NUM_COARSE_BUCKETS][COARSE_BUCKET_ITEMS];
        //};
    //};

    //fine_hashtab scratch_ht;                     /*   3 200 bytes */
    // 128
    private final byte[] scratch_counts = new byte[NUM_FINE_BUCKETS];
    // 128 * 12 * 2 = 3072
    private final char[][] scratch_buckets = new char[NUM_FINE_BUCKETS][FINE_BUCKET_ITEMS];

    void CLEAR_STAGE1() {
        Arrays.fill(stage1_counts, (char) 0);
    }

    void CLEAR_STAGE2() {
        Arrays.fill(stage2_counts, (char) 0);
    }

    void CLEAR_STAGE3() {
        Arrays.fill(stage3_counts, (char) 0);
    }

    void CLEAR_SCRATCH() {
        Arrays.fill(scratch_counts, (byte) 0);
    }

    static int MAKE_ITEM(int bucket, int left, int right) {
        return (left << 17) | (right << 8) | bucket;
    }

    static int ITEM_BUCKET(int item) {
        return item & NUM_COARSE_BUCKETS_MASK;
    }

    static int ITEM_LEFT_IDX(int item) {
        return item >> 17;
    }

    static int ITEM_RIGHT_IDX(int item) {
        return (item >> 8) & 511;
    }

    static int INVERT_BUCKET(int idx) {
        return (0 - idx) & NUM_COARSE_BUCKETS_MASK;
    }

    static int INVERT_SCRATCH(int idx) {
        return (0 - idx) & NUM_FINE_BUCKETS_MASK;
    }

    char STAGE1_IDX(int buck, int pos) {
        return stage1_buckets[buck][pos];
    }

    int STAGE2_IDX(int buck, int pos) {
        return stage2_buckets[buck][pos];
    }

    int STAGE3_IDX(int buck, int pos) {
        return stage3_buckets[buck][pos];
    }

    void SET_STAGE1_IDX(int buck, int pos, int val) {
        stage1_buckets[buck][pos] = (char) val;
    }

    void SET_STAGE2_IDX(int buck, int pos, int val) {
        stage2_buckets[buck][pos] = val;
    }

    void SET_STAGE3_IDX(int buck, int pos, int val) {
        stage3_buckets[buck][pos] = val;
    }

    long STAGE1_DATA(int buck, int pos) {
        return stage1_data_buckets[buck][pos];
    }

    long STAGE2_DATA(int buck, int pos) {
        return stage2_data_buckets[buck][pos];
    }

    int STAGE3_DATA(int buck, int pos) {
        return stage3_data_buckets[buck][pos];
    }

    void SET_STAGE1_DATA(int buck, int pos, long val) {
        stage1_data_buckets[buck][pos] = val;
    }

    void SET_STAGE2_DATA(int buck, int pos, long val) {
        stage2_data_buckets[buck][pos] = val;
    }

    void SET_STAGE3_DATA(int buck, int pos, int val) {
        stage3_data_buckets[buck][pos] = val;
    }

    int STAGE1_SIZE(int buck) {
        return stage1_counts[buck];
    }

    int STAGE2_SIZE(int buck) {
        return stage2_counts[buck];
    }

    int STAGE3_SIZE(int buck) {
        return stage3_counts[buck];
    }

    void SET_STAGE1_SIZE(int buck, int val) {
        stage1_counts[buck] = (char) val;
    }

    void SET_STAGE2_SIZE(int buck, int val) {
        stage2_counts[buck] = (char) val;
    }

    void SET_STAGE3_SIZE(int buck, int val) {
        stage3_counts[buck] = (char) val;
    }

    char SCRATCH(int buck, int pos) {
        return scratch_buckets[buck][pos];
    }

    void SET_SCRATCH(int buck, int pos, int val) {
        scratch_buckets[buck][pos] = (char) val;
    }

    int SCRATCH_SIZE(int buck) {
        return scratch_counts[buck] & 0xff;
    }

    void SET_SCRATCH_SIZE(int buck, int val) {
        scratch_counts[buck] = (byte) val;
    }

    void MAKE_PAIRS1(int bucket_idx, int item_idx, int cpl_bucket) {
        //System.out.println("MAKE_PAIRS1 bkt " + bucket_idx + " item " + item_idx + " cplb " + cpl_bucket);
        long value = STAGE1_DATA(bucket_idx, item_idx);
        if (bucket_idx != 0) // CARRY
            value++;
        int fine_buck_idx = (int) (value & NUM_FINE_BUCKETS_MASK);
        int fine_cpl_bucket = INVERT_SCRATCH(fine_buck_idx);
        int fine_cpl_size = SCRATCH_SIZE(fine_cpl_bucket);
        for (int fine_idx = 0; fine_idx < fine_cpl_size; ++fine_idx) {
            int cpl_index = SCRATCH(fine_cpl_bucket, fine_idx);
            long cpl_value = STAGE1_DATA(cpl_bucket, cpl_index);
            long sum = value + cpl_value;
            assert((sum & NUM_FINE_BUCKETS_MASK) == 0);
            // 45 bits
            sum >>= NUM_FINE_BUCKETS_BITS;
            sum &= 0x1fffffffffffL;
            int s2_buck_id = (int) (sum & NUM_COARSE_BUCKETS_MASK);
            int s2_item_id = STAGE2_SIZE(s2_buck_id);
            if (s2_item_id >= COARSE_BUCKET_ITEMS)
                continue;
            SET_STAGE2_SIZE(s2_buck_id, s2_item_id + 1);
            SET_STAGE2_IDX(s2_buck_id, s2_item_id,
                           MAKE_ITEM(bucket_idx, item_idx, cpl_index));
            // 37 bits
            sum >>= NUM_COARSE_BUCKETS_BITS;
            SET_STAGE2_DATA(s2_buck_id, s2_item_id, sum);
        }
    }

    void MAKE_PAIRS2(int bucket_idx, int item_idx, int cpl_bucket) {
        //System.out.println("MAKE_PAIRS2 bkt " + bucket_idx + " item " + item_idx + " cplb " + cpl_bucket);
        long value = STAGE2_DATA(bucket_idx, item_idx);
        if (bucket_idx != 0) // CARRY
            value++;
        int fine_buck_idx = (int) (value & NUM_FINE_BUCKETS_MASK);
        int fine_cpl_bucket = INVERT_SCRATCH(fine_buck_idx);
        int fine_cpl_size = SCRATCH_SIZE(fine_cpl_bucket);
        for (int fine_idx = 0; fine_idx < fine_cpl_size; ++fine_idx) {
            int cpl_index = SCRATCH(fine_cpl_bucket, fine_idx);
            long cpl_value = STAGE2_DATA(cpl_bucket, cpl_index);
            long sum = value + cpl_value;
            assert((sum & NUM_FINE_BUCKETS_MASK) == 0);
            // 30 bits
            sum >>= NUM_FINE_BUCKETS_BITS;
            sum &= 0x3fffffffL;
            int s3_buck_id = (int) (sum & NUM_COARSE_BUCKETS_MASK);
            int s3_item_id = STAGE3_SIZE(s3_buck_id);
            if (s3_item_id >= COARSE_BUCKET_ITEMS)
                continue;
            SET_STAGE3_SIZE(s3_buck_id, s3_item_id + 1);
            SET_STAGE3_IDX(s3_buck_id, s3_item_id,
                           MAKE_ITEM(bucket_idx, item_idx, cpl_index));
            // 22 bits
            sum >>= NUM_COARSE_BUCKETS_BITS;
            SET_STAGE3_DATA(s3_buck_id, s3_item_id, (int) sum);
        }
    }

}
