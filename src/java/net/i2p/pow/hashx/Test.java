package net.i2p.pow.hashx;

import net.i2p.data.DataHelper;

class Test {
    static int test_no = 0;

    private static final int HASHX_SIZE = 16;
    private static final HXCtx ctx_int = new HXCtx(512);

    // must have trailing null to match what C test does
    private static byte seed1[] = DataHelper.getASCII("This is a test\0");
    private static byte seed2[] = DataHelper.getASCII("Lorem ipsum dolor sit amet\0");

    private static final long counter1 = 0;
    private static final long counter2 = 123456;
    private static final long counter3 = 987654321123456789L;

    private static boolean equals_hex(byte[] h1, String hex) {
        hex = hex.substring(0, 32);
        String s = DataHelper.toString(h1, 16);
        System.out.println("expected:   " + hex);
        System.out.println("calculated: " + s);
        return s.equals(hex);
    }

    private static void run_test(boolean ok) {
        System.out.println("Test " + (++test_no) + (ok ? " PASSED" : " SKIPPED"));
    }

    private static boolean test_make1() {
        boolean result = HashX.make(ctx_int, seed1, seed1.length);
        assert(result);
        return true;
    }

    private static boolean test_hash_ctr1() {
        byte[] hash = new byte[HASHX_SIZE];
        // yes test 1 uses counter 2 in the C test
        HashX.exec(ctx_int, counter2, hash);
        assert(equals_hex(hash, "aebdd50aa67c93afb82a4c534603b65e46decd584c55161c526ebc099415ccf1"));
        return true;
    }

    private static boolean test_hash_ctr2() {
        byte[] hash = new byte[HASHX_SIZE];
        // yes test 2 uses counter 1 in the C test
        HashX.exec(ctx_int, counter1, hash);
        assert(equals_hex(hash, "2b2f54567dcbea98fdb5d5e5ce9a65983c4a4e35ab1464b1efb61e83b7074bb2"));
        return true;
    }

    private static boolean test_make2() {
        boolean result = HashX.make(ctx_int, seed2, seed2.length);
        assert(result);
        return true;
    }

    private static boolean test_hash_ctr3() {
        byte[] hash = new byte[HASHX_SIZE];
        // yes test 3 uses counter 2 in the C test
        long start = System.currentTimeMillis();
        for (int i = 0; i < 65536; i++) {
            HashX.exec(ctx_int, counter2, hash);
        }
        System.out.println("64k interpreted runs took " + (System.currentTimeMillis() - start));
        assert(equals_hex(hash, "ab3d155bf4bbb0aa3a71b7801089826186e44300e6932e6ffd287cf302bbb0ba"));
        return true;
    }

    private static boolean test_hash_ctr4() {
        byte[] hash = new byte[HASHX_SIZE];
        // yes test 4 uses counter 3 in the C test
        HashX.exec(ctx_int, counter3, hash);
        assert(equals_hex(hash, "8dfef0497c323274a60d1d93292b68d9a0496379ba407b4341cf868a14d30113"));
        return true;
    }

    private static boolean test_make3() {
        byte[] hash = new byte[HASHX_SIZE];
        ctx_int.state = CompiledState.REQUESTED;
        HashX.exec(ctx_int, 1, hash);
        return true;
    }

    private static boolean test_compiler_ctr1() {
        byte[] hash = new byte[HASHX_SIZE];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 65536; i++) {
            HashX.exec(ctx_int, counter2, hash);
        }
        System.out.println("64k compiled runs took " + (System.currentTimeMillis() - start));
        assert(equals_hex(hash, "ab3d155bf4bbb0aa3a71b7801089826186e44300e6932e6ffd287cf302bbb0ba"));
        return true;
    }

    public static void main(String[] args) {
        run_test(test_make1());
        run_test(test_hash_ctr1());
        run_test(test_hash_ctr2());
        run_test(test_make2());
        run_test(test_hash_ctr3());
        run_test(test_hash_ctr4());
        run_test(test_make3());
        run_test(test_compiler_ctr1());
        System.out.println("\nAll tests were successful\n");
    }
}
