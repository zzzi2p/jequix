package net.i2p.pow.hashx;

import java.security.MessageDigest;
import java.util.Arrays;

import com.southernstorm.noise.crypto.blake.Blake2bMessageDigest;

import net.i2p.data.DataHelper;


/**
 *  Entry point
 */
public class HashX {

    private static final byte[] HASHX_SALT = new byte[16];
    static {
         // 8 bytes, blake expects 16
         byte[] salt = DataHelper.getASCII("HashX v1");
         System.arraycopy(salt, 0, HASHX_SALT, 0, salt.length);
    }
    private static final String HASHX_PERSONAL = null;
    private static int runs;

    /**
     *  @return success
     */
    public static boolean make(HXCtx ctx, byte[] seed, int size) {
        long start = System.currentTimeMillis();
        ctx.seed = Arrays.copyOf(seed, size);
        MessageDigest blake = new Blake2bMessageDigest(HASHX_PERSONAL, HASHX_SALT);
        blake.update(seed, 0, size);
        byte[] hash = blake.digest();
        // program keys
        long[] pkeys = new long[4];
        for (int i = 0; i < 4; i++) {
            // program generation keys
            pkeys[i] = fromLong8LE(hash, i * 8);
            // program execution keys
            ctx.keys[i] = fromLong8LE(hash, 32 + (i * 8));
        }
        //System.out.println("init seed\n" + net.i2p.util.HexDump.dump(seed, 0, size));
        //System.out.println("init keys\n" + net.i2p.util.HexDump.dump(hash));
        //System.out.println("init pkeys");
        //print_registers("prog key", pkeys, 4);
        //System.out.println("init ekeys");
        //print_registers("exec key", ctx.keys, 4);
        boolean rv = Program.generate(pkeys, ctx);
        System.out.println("make() took " + (System.currentTimeMillis() - start));
        //System.out.println("program:");
        //print_program(ctx);
        return rv;
    }

    public static void exec(HXCtx ctx, long input, byte[] output) {
        //long start = System.nanoTime();
        long[] r = new long[8];
        SipHash.ctr_state512(ctx.keys, input, r);
        //System.out.println("init regs");
        //print_registers("R", r, 8);
        if (ctx.code.length != GenCtx.REQUIREMENT_SIZE)
            throw new IllegalArgumentException();
        boolean executed = false;
        if (ctx.compiled) {
            executed = Compiler.execute(ctx, r, Long.toUnsignedString(input));
        }
        if (!executed && ctx.request_compile && !ctx.compile_failed) {
            executed = Compiler.compile(ctx, r, Long.toUnsignedString(input));
            if (!executed) {
                System.out.println("FAILED compiling program " + input);
            }
        }
        if (!executed) {
            Exec.execute(ctx.code, r);
        }
        //System.out.println("after exec regs");
        //print_registers("R", r, 8);
        /* Hash finalization to remove bias toward 0 caused by multiplications */
        r[0] += ctx.keys[0];
        r[1] += ctx.keys[1];
        r[6] += ctx.keys[2];
        r[7] += ctx.keys[3];
        /* 1 SipRound per 4 registers is enough to pass SMHasher. */
        SipHash.SIPROUND(r);
        SipHash.SIPROUND(r, 4);
        //System.out.println("finalized regs");
        //print_registers("R", r, 8);

        toLong8LE(output, 0, r[0] ^ r[4]);
        if (output.length >= 16)
            toLong8LE(output, 8, r[1] ^ r[5]);
        if (output.length >= 24)
            toLong8LE(output, 16, r[2] ^ r[6]);
        if (output.length >= 32)
            toLong8LE(output, 24, r[3] ^ r[7]);
        //System.out.println("exec() run " + (++runs) + " took " + ((System.nanoTime() - start) / 1000) + " us");
    }

    private static void print_registers(String pfx, long[] r, int cnt) {
        for (int i = 0; i < cnt; i++) {
            System.out.println("   " + pfx + i + ": " + Long.toUnsignedString(r[i]));
        }
    }

    private static void print_program(HXCtx ctx) {
        for (int i = 0; i < ctx.code.length; i++) {
            System.out.println(i + ": " + ctx.code[i]);
        }
    }

    /**
     * Little endian.
     * Same as DataHelper.fromlongLE(src, offset, 8) but allows negative result
     *
     * @throws ArrayIndexOutOfBoundsException
     * @since 0.9.36
     */
    public static long fromLong8LE(byte src[], int offset) {
        long rv = 0;
        for (int i = offset + 7; i >= offset; i--) {
            rv <<= 8;
            rv |= src[i] & 0xFF;
        }
        return rv;
    }
    
    /**
     * Little endian.
     * Same as DataHelper.fromlongLE(target, offset, 8, value) but allows negative value
     *
     */
    public static void toLong8LE(byte target[], int offset, long value) {
        int limit = offset + 8;
        for (int i = offset; i < limit; i++) {
            target[i] = (byte) value;
            value >>= 8;
        }
    }
}
