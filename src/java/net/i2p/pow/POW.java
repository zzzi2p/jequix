package net.i2p.pow;

import java.security.MessageDigest;

import net.i2p.data.DataHelper;
import net.i2p.data.Hash;
import net.i2p.util.HexDump;
import net.i2p.util.RandomSource;

import com.southernstorm.noise.crypto.blake.Blake2bMessageDigest;

import net.i2p.pow.equix.Equix;
import net.i2p.pow.equix.Heap;
import net.i2p.pow.hashx.HXCtx;

public class POW {
    private static final byte[] P = DataHelper.getASCII("Tor hs intro v1\0");
    private static final int SEED_LEN = 32;
    private static final int NONCE_LEN = 16;
    private static final int EFFORT_LEN = 4;
    // 100
    private static final int CHALLENGE_LEN = P.length + Hash.HASH_LENGTH + SEED_LEN + NONCE_LEN + EFFORT_LEN;
    private static final int SOLUTION_LEN = 16;
    private static final int SEED_PFX_LEN = 4;
    // 116
    private static final int CHECK_LEN = CHALLENGE_LEN + SOLUTION_LEN;
    // 40
    private static final int PROOF_LEN = NONCE_LEN + SOLUTION_LEN + EFFORT_LEN + SEED_PFX_LEN;
    private static final int BLAKE2B_32_LEN = 4;

    /**
     *  @return 40 byte proof
     */
    public static byte[] solve(Hash hash, byte[] seed, int effort, int maxruns) {
        // leave room so we use the same buffer for the check
        byte[] challenge = new byte[CHECK_LEN];
        System.arraycopy(P, 0, challenge, 0, P.length);
        int off = P.length;
        System.arraycopy(hash.getData(), 0, challenge, off, Hash.HASH_LENGTH);
        off += Hash.HASH_LENGTH;
        System.arraycopy(seed, 0, challenge, off, SEED_LEN);
        off += SEED_LEN;
        RandomSource.getInstance().nextBytes(challenge, off, NONCE_LEN);
        off += NONCE_LEN;
        DataHelper.toLong(challenge, off, 4, effort);
        System.out.println("Challenge:\n" + HexDump.dump(challenge, 0, CHALLENGE_LEN));

        HXCtx ctx = new HXCtx(512);
        Heap heap = new Heap();
        char[][] solutions = new char[8][8];

        int runs = 0;
        while (runs++ < maxruns) {
            int count = Equix.solve(ctx, heap, challenge, CHALLENGE_LEN, solutions, 0);
            if (count == 0) {
                System.out.println("No solutions found on run " + runs);
                incrementNonce(challenge);
                continue;
            }

            MessageDigest blake = new Blake2bMessageDigest(null, null, BLAKE2B_32_LEN);
            for (int i = 0; i < count; i++) {
                char[] solution = solutions[i];
                off = CHALLENGE_LEN;
                for (int j = 0; j < 8; j++) {
                    DataHelper.toLong(challenge, off, 2, solution[j]);
                    off += 2;
                }
                //System.out.println("check:\n" + HexDump.dump(challenge));
                blake.update(challenge, 0, CHECK_LEN);
                byte[] bhash = blake.digest();
                long r = DataHelper.fromLong(bhash, 0, BLAKE2B_32_LEN);
                long m = r * effort;
                if (m > 0xffffffffL) {
                    System.out.println("Solution " + i + " does not meet effort " + effort);
                    continue;
                }
                byte[] rv = new byte[PROOF_LEN];
                System.arraycopy(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN, rv, 0, NONCE_LEN);
                off = NONCE_LEN;
                System.arraycopy(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN + NONCE_LEN, rv, off, EFFORT_LEN);
                off += EFFORT_LEN;
                System.arraycopy(challenge, CHALLENGE_LEN, rv, off, SOLUTION_LEN);
                off += SOLUTION_LEN;
                System.arraycopy(seed, 0, rv, off, SEED_PFX_LEN);
                System.out.println("Found proof with effort " + effort + " on run " + runs);
                return rv;
            }
            System.out.println("No solutions meet effort " + effort + " on run " + runs);
            incrementNonce(challenge);
        }
        return null;
    }

    private static void incrementNonce(byte[] challenge) {
        // tor spec says treat as a 16 byte LE value,
        // but we're not going around that many times, just do the first 8 bytes,
        // big endian, doesn't matter, it's a random value
        long n = DataHelper.fromLong8(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN);
        n++;
        DataHelper.toLong8(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN, n);
    }

    public static void main(String[] args) {
        Hash h = Hash.FAKE_HASH;
        byte[] seed = h.getData();
        byte[] proof = solve(h, seed, 8, 999);
        if (proof != null)
            System.out.println("Found solution:\n" + HexDump.dump(proof));
        else
            System.out.println("No solution found");

    }

}
