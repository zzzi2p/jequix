package net.i2p.pow.hashx;

/**
 *  Execution port
 */
enum EPort {
    PORT_NONE(0),
    PORT_P0(1),
    PORT_P1(2),
    PORT_P01(1+2),
    PORT_P5(4),
    PORT_P05(1+4),
    PORT_P015(1+2+4);

    final int port;

    private EPort(int port) { this.port = port; }
}
