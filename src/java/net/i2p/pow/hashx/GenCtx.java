package net.i2p.pow.hashx;

/**
 *  Generator Context
 *  Containing the generation keys and the
 *  virtual machine registers
 */
class GenCtx {
    static final int TARGET_CYCLE  = 192;
    static final int REQUIREMENT_SIZE = 512;
    static final int REQUIREMENT_MUL_COUNT = 192;
    static final int REQUIREMENT_LATENCY = 195;
    static final int REGISTER_NEEDS_DISPLACEMENT = 5;
    static final int PORT_MAP_SIZE = TARGET_CYCLE + 4;
    static final int NUM_PORTS = 3;
    static final int MAX_RETRIES = 1;
    static final int LOG2_BRANCH_PROB = 4;

    static final int HASHX_PROGRAM_MAX_SIZE = 512;

    final RegInfo[] registers;
    final EPort[][] ports;
    final SipHashRNG gen;
    int cycle;
    int sub_cycle;
    int mul_count;
    boolean chain_mul;
    int latency;

    public GenCtx(long[] keys) {
        registers = new RegInfo[8];
        for (int i = 0; i < 8; i++) {
            registers[i] = new RegInfo();
        }

        ports = new EPort[PORT_MAP_SIZE][NUM_PORTS];
        for (int i = 0; i < PORT_MAP_SIZE; i++) {
            ports[i] = new EPort[NUM_PORTS];
            for (int j = 0; j < NUM_PORTS; j++) {
                ports[i][j] = EPort.PORT_NONE;
            }
        }

        gen = new SipHashRNG(keys);
    }
}
