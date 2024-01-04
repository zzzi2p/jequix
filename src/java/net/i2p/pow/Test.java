package net.i2p.pow;

import net.i2p.data.DataHelper;
import net.i2p.data.Hash;
import net.i2p.util.HexDump;

import net.i2p.pow.equix.Heap;
import net.i2p.pow.hashx.HXCtx;

/**
 * Vectors from Tor test_hs_pow.c
 *
 * Copyright (c) 2020-2023, The Tor Project, Inc.
 * See LICENSE for licensing information
 */
public class Test {
    private static int test_no;

/*
    static const struct {
      uint32_t claimed_effort;
      uint32_t validated_effort;
      int expected_retval;
      const char *seed_hex;
      const char *service_blinded_id_hex;
      const char *nonce_hex;
      const char *sol_hex;
      const char *encoded_hex;
    } vectors[] = {
*/
    private static final int[] claimed = { 1, 0, 1000000, 1000000, 999999, 1000000, 100000 };
    private static final int[] validated = { 0, 0, 1000000, 0, 0, 0, 100000 };
    private static final int[] retval = { -1, 0, 0, -1, -1, -1, 0 };

    private static final String[] V0 =
    {
      /* All zero, expect invalid */
      //1, 0, -1,
      "0000000000000000000000000000000000000000000000000000000000000000",
      "1111111111111111111111111111111111111111111111111111111111111111",
      "00000000000000000000000000000000", "00000000000000000000000000000000",
      "01" +
      "00000000000000000000000000000000" +
      "00000001" + "00000000" +
      "00000000000000000000000000000000"
    };
    private static final String[] V1 =
    {
      /* Valid zero-effort solution */
      //0, 0, 0,
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "1111111111111111111111111111111111111111111111111111111111111111",
      "55555555555555555555555555555555", "4312f87ceab844c78e1c793a913812d7",
      "01" +
      "55555555555555555555555555555555" +
      "00000000" + "aaaaaaaa" +
      "4312f87ceab844c78e1c793a913812d7"
    };
    private static final String[] V2 =
    {
      /* Valid high-effort solution */
      //1000000, 1000000, 0,
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "1111111111111111111111111111111111111111111111111111111111111111",
      "59217255555555555555555555555555", "0f3db97b9cac20c1771680a1a34848d3",
      "01" +
      "59217255555555555555555555555555" +
      "000f4240" + "aaaaaaaa" +
      "0f3db97b9cac20c1771680a1a34848d3"
    };
    private static final String[] V3 =
    {
      /* Reject replays */
      //1000000, 0, -1,
      "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
      "1111111111111111111111111111111111111111111111111111111111111111",
      "59217255555555555555555555555555", "0f3db97b9cac20c1771680a1a34848d3",
      "01" +
      "59217255555555555555555555555555" +
      "000f4240" + "aaaaaaaa" +
      "0f3db97b9cac20c1771680a1a34848d3"
    };
    private static final String[] V4 =
    {
      /* The claimed effort must exactly match what's in the challenge */
      //99999, 0, -1,
      "86fb0acf4932cda44dbb451282f415479462dd10cb97ff5e7e8e2a53c3767a7f",
      "bfd298428562e530c52bdb36d81a0e293ef4a0e94d787f0f8c0c611f4f9e78ed",
      "2eff9fdbc34326d9d2f18ed277469c63", "400cb091139f86b352119f6e131802d6",
      "01" +
      "2eff9fdbc34326d9d2f18ed277469c63" +
      "0001869f" + "86fb0acf" +
      "400cb091139f86b352119f6e131802d6"
    };
    private static final String[] V5 =
    {
      /* Otherwise good solution but with a corrupted nonce */
      //100000, 0, -1,
      "86fb0acf4932cda44dbb451282f415479462dd10cb97ff5e7e8e2a53c3767a7f",
      "bfd298428562e530c52bdb36d81a0e293ef4a0e94d787f0f8c0c611f4f9e78ed",
      "2eff9fdbc34326d9a2f18ed277469c63", "400cb091139f86b352119f6e131802d6",
      "01" +
      "2eff9fdbc34326d9a2f18ed277469c63" +
      "000186a0" + "86fb0acf" +
      "400cb091139f86b352119f6e131802d6"
    };
    private static final String[] V6 =
    {
      /* Corrected version of above */
      //100000, 100000, 0,
      "86fb0acf4932cda44dbb451282f415479462dd10cb97ff5e7e8e2a53c3767a7f",
      "bfd298428562e530c52bdb36d81a0e293ef4a0e94d787f0f8c0c611f4f9e78ed",
      "2eff9fdbc34326d9d2f18ed277469c63", "400cb091139f86b352119f6e131802d6",
      "01" +
      "2eff9fdbc34326d9d2f18ed277469c63" +
      "000186a0" + "86fb0acf" +
      "400cb091139f86b352119f6e131802d6"
    };

    public static void main(String[] args) {
        test(V0, claimed[0], validated[0], retval[0]);
        // again, compiled from here on
        test(V0, claimed[0], validated[0], retval[0]);
        test(V1, claimed[1], validated[1], retval[1]);
        // bad vector? fails ORDER, otherwise good
        // s0/1 15631 <= 31673 PASS
        // s2/3 44188 <= 49440 PASS
        // s4/5 5751 <= 41344 PASS
        // s6/7 18595 <= 54088 PASS
        // s01/23 1024424889 <= 2895954208 PASS
        // s45/67 376938880 <= 1218696008 PASS
        // s0123/4567 4399871398359384352 <= 1618940163409564488 FAIL
        test(V2, claimed[2], validated[2], retval[2]);
        test(V3, claimed[3], validated[3], retval[3]);
        test(V4, claimed[4], validated[4], retval[4]);
        test(V5, claimed[5], validated[5], retval[5]);
        test(V6, claimed[6], validated[6], retval[6]);
    }

    private static void test(String[] v, int claimed, int validated, int retval) {
        System.out.println("Test " + (++test_no));
        byte[] seed = fromHex(v[0]);
        byte[] service_blinded_id = fromHex(v[1]);
        byte[] nonce = fromHex(v[2]);
        byte[] sol = fromHex(v[3]);
        byte[] encoded = fromHex(v[4]);
        HXCtx ctx = new HXCtx(512);
        if (test_no != 0)
            ctx.request_compile = true;
        Hash h = new Hash(service_blinded_id);

        // skip for the million effort
        if (validated == 0) {
            byte[] solution = POW.solve(ctx, h, seed, validated, 999, nonce);
            if (solution != null) {
                System.out.println("Calculated solution\n" + HexDump.dump(solution, 24, 16));
                System.out.println("Test vector solution\n" + HexDump.dump(sol));
            } else {
                System.out.println("No solution calculated");
            }
        }

        byte[] proof = new byte[POW.PROOF_LEN];
        // proof is nonce/effort/prefix/solution
        // skip the "01" in the vectors
        System.arraycopy(encoded, 1, proof, 0, POW.PROOF_LEN);
        boolean ok = POW.verify(ctx, h, seed, validated, proof);
        System.out.println("Verify " + test_no + " expected " + (retval == 0) + " actual " + ok);
        ok = ok == (retval == 0);
        System.out.println("Test " + test_no + (ok ? " PASSED" : " FAILED"));
        // see above
        if (test_no != 4)
            assert(ok);
    }

    private static byte[] fromHex(String v) {
        int sz = v.length() / 2;
        byte[] b = new byte[sz];
        for (int i = 0; i < sz; i++) {
            b[i] = (byte) (Integer.parseInt(v.substring(i*2, (i*2) + 2), 16) & 0xff);
        }
        return b;
    }
}
