package net.i2p.pow.hashx;

import java.lang.reflect.Method;

/**
 *  Top-level context
 *  Containing the generated program
 *  and the execution keys
 */
public class HXCtx {
    public final Instr[] code;
    public int code_size;
    public final long[] keys;
    public byte[] seed;
    public boolean request_compile, compiled, compile_failed;
    public Method compiled_method;

    public HXCtx(int sz) {
        code = new Instr[sz];
        for (int i = 0; i < sz; i++) {
            code[i] = new Instr();
        }
        keys = new long[4];
    }
}
