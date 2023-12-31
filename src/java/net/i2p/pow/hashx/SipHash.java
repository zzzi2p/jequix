package net.i2p.pow.hashx;

/**
 *  SipHash
 */
class SipHash {


    /**
     *  For the RNG.
     *  @param keys 4 longs
     */
    public static long hash13_ctr(long input, long[] keys) {
        long[] v = new long[4];
        System.arraycopy(keys, 0, v, 0, 4);

        v[3] ^= input;

        SIPROUND(v);

        v[0] ^= input;
        v[2] ^= 0xff;

        SIPROUND(v);
        SIPROUND(v);
        SIPROUND(v);

        return (v[0] ^ v[1]) ^ (v[2] ^ v[3]);
    }

    /**
     *  For initializing the registers before execution.
     *  @param keys 4 longs
     *  @param output 8 longs
     */
    public static void ctr_state512(long[] keys, long input, long[] output) {
        long[] v = new long[4];
        System.arraycopy(keys, 0, v, 0, 4);

        v[1] ^= 0xee;
        v[3] ^= input;

        SIPROUND(v);
        SIPROUND(v);

        v[0] ^= input;
        v[2] ^= 0xee;

        SIPROUND(v);
        SIPROUND(v);
        SIPROUND(v);
        SIPROUND(v);

        System.arraycopy(v, 0, output, 0, 4);

        v[1] ^= 0xdd;

        SIPROUND(v);
        SIPROUND(v);
        SIPROUND(v);
        SIPROUND(v);

        System.arraycopy(v, 0, output, 4, 4);
    }

    public static void SIPROUND(long[] v) {
        v[0] += v[1]; v[2] += v[3]; v[1] = Long.rotateLeft(v[1], 13);
        v[3] = Long.rotateLeft(v[3], 16); v[1] ^= v[0]; v[3] ^= v[2];
        v[0] = Long.rotateLeft(v[0], 32); v[2] += v[1]; v[0] += v[3];
        v[1] = Long.rotateLeft(v[1], 17);  v[3] = Long.rotateLeft(v[3], 21);
        v[1] ^= v[2]; v[3] ^= v[0]; v[2] = Long.rotateLeft(v[2], 32);
    }

    public static void SIPROUND(long[] v, int off) {
        v[off] += v[off + 1]; v[off + 2] += v[off + 3]; v[off + 1] = Long.rotateLeft(v[off + 1], 13);
        v[off + 3] = Long.rotateLeft(v[off + 3], 16); v[off + 1] ^= v[off]; v[off + 3] ^= v[off + 2];
        v[off] = Long.rotateLeft(v[off], 32); v[off + 2] += v[off + 1]; v[off] += v[off + 3];
        v[off + 1] = Long.rotateLeft(v[off + 1], 17);  v[off + 3] = Long.rotateLeft(v[off + 3], 21);
        v[off + 1] ^= v[off + 2]; v[off + 3] ^= v[off]; v[off + 2] = Long.rotateLeft(v[off + 2], 32);
    }
}
