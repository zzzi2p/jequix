package net.i2p.pow.hashx;

/**
 *  SipHash RNG.
 *  A deterministic RNG seeded by keys.
 *  Must create the same output as the C version for the same keys.
 */
class SipHashRNG {
    private final long[] keys;
    private int count8, count32;
    private long counter, buffer8, buffer32;

    /**
     *  @param keys 4 longs
     */
    public SipHashRNG(long[] keys) {
        this.keys = keys;
    }

    public byte nextByte() {
        if (count8 == 0) {
            buffer8 = SipHash.hash13_ctr(counter, keys);
            counter++;
            count8 = 8;
        }
        count8--;
        return (byte) (buffer8 >> (count8 * 8));
    }

    /**
     *  long so unsigned
     */
    public long nextInt() {
        if (count32 == 0) {
            buffer32 = SipHash.hash13_ctr(counter, keys);
            counter++;
            count32 = 2;
        }
        count32--;
        long rv = (buffer32 >> (count32 * 32)) & 0xffffffffL;
        return rv;
    }

}
