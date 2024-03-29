package net.i2p.pow;

import java.security.MessageDigest;

import net.i2p.data.DataHelper;
import net.i2p.data.Hash;
import net.i2p.util.HexDump;
import net.i2p.util.RandomSource;

import com.southernstorm.noise.crypto.blake.Blake2bMessageDigest;

import net.i2p.pow.equix.Equix;
import net.i2p.pow.equix.Result;
import net.i2p.pow.equix.Heap;
import net.i2p.pow.hashx.CompiledState;
import net.i2p.pow.hashx.HXCtx;

/**
 *  ref: https://spec.torproject.org/hspow-spec/v1-equix.html
 *  ref: https://spec.torproject.org/rend-spec/introduction-protocol.html#INTRO1_POW_EXT
 */
public class POW {
    public static final byte[] P = DataHelper.getASCII("Tor hs intro v1\0");
    public static final int SEED_LEN = 32;
    public static final int NONCE_LEN = 16;
    public static final int EFFORT_LEN = 4;
    // 100
    public static final int CHALLENGE_LEN = P.length + Hash.HASH_LENGTH + SEED_LEN + NONCE_LEN + EFFORT_LEN;
    public static final int SOLUTION_LEN = 16;
    public static final int SEED_PFX_LEN = 4;
    // 116
    public static final int CHECK_LEN = CHALLENGE_LEN + SOLUTION_LEN;
    // 40
    public static final int PROOF_LEN = NONCE_LEN + EFFORT_LEN + SEED_PFX_LEN + SOLUTION_LEN;
    public static final int BLAKE2B_32_LEN = 4;

    private static final boolean FIND_BEST_SOLUTION = true;

    /**
     *  Proof is 16 byte nonce, 4 byte effort, 4 byte seed prefix, 16 byte solution
     *  Use a random 16 byte nonce
     *
     *  @return 40 byte proof or null
     */
    public static byte[] solve(HXCtx ctx, Hash hash, byte[] seed, int effort, int maxruns) {
        return solve(ctx, hash, seed, effort, maxruns, null);
    }

    /**
     *  Proof is 16 byte nonce, 4 byte effort, 4 byte seed prefix, 16 byte solution
     *
     *  @param nonce 16 bytes, or null to start with a random one
     *  @return 40 byte proof or null
     */
    public static byte[] solve(HXCtx ctx, Hash hash, byte[] seed, int effort, int maxruns, byte[] initialNonce) {
        long start = System.currentTimeMillis();
        // create the 100 byte challenge:
        // 16 byte P string, 32 byte hash, 32 byte seed, 16 byte nonce, 4 byte effort
        // leave room so we use the same buffer for the check
        byte[] challenge = new byte[CHECK_LEN];
        System.arraycopy(P, 0, challenge, 0, P.length);
        int off = P.length;
        System.arraycopy(hash.getData(), 0, challenge, off, Hash.HASH_LENGTH);
        off += Hash.HASH_LENGTH;
        System.arraycopy(seed, 0, challenge, off, SEED_LEN);
        off += SEED_LEN;
        if (initialNonce != null)
            System.arraycopy(initialNonce, 0, challenge, off, NONCE_LEN);
        else
            RandomSource.getInstance().nextBytes(challenge, off, NONCE_LEN);
        off += NONCE_LEN;
        DataHelper.toLong(challenge, off, 4, effort);
        //System.out.println("Challenge:\n" + HexDump.dump(challenge, 0, CHALLENGE_LEN));

        Heap heap = new Heap();
        char[][] solutions = new char[8][8];

        int runs = 0;
        MessageDigest blake = new Blake2bMessageDigest(null, null, BLAKE2B_32_LEN);
        while (runs++ < maxruns) {
            System.out.println("Starting run " + runs);
            if (effort > 2) {
                ctx.state = CompiledState.REQUESTED;
                ctx.threads = 4;
            }
            int count = Equix.solve(ctx, heap, challenge, CHALLENGE_LEN, solutions, 0);
            if (count == 0) {
                System.out.println("No solutions found on run " + runs);
                incrementNonce(challenge);
                continue;
            }

            // find the solution with the best effort, not the first one,
            // if server is sorting by actual effort
            char[] bestSolution = null;
            long bestM = Long.MAX_VALUE;
            for (int i = 0; i < count; i++) {
                char[] solution = solutions[i];
                off = CHALLENGE_LEN;
                // append the 16 byte solution to the challenge
                for (int j = 0; j < 8; j++) {
                    DataHelper.toLongLE(challenge, off, 2, solution[j]);
                    off += 2;
                }
                // blake2b-32 of the 116 byte check bytes
                //System.out.println("check:\n" + HexDump.dump(challenge));
                blake.update(challenge, 0, CHECK_LEN);
                byte[] bhash = blake.digest();
                // r is the 4 byte check value
                long r = DataHelper.fromLong(bhash, 0, BLAKE2B_32_LEN);
                long m = r * effort;
                if (m > 0xffffffffL) {
                    System.out.println("Solution " + i + " does not meet effort " + effort);
                    continue;
                }
                if (m < bestM) {
                    if (bestSolution == null)
                        System.out.println("Found first solution " + i + " with actual effort " + (0xffffffffL / (double) r));
                    else
                        System.out.println("Found better solution " + i + " with actual effort " + (0xffffffffL / (double) r));
                    bestM = m;
                    bestSolution = solution;
                    if (!FIND_BEST_SOLUTION)
                        break;
                } else {
                    System.out.println("Solution " + i + " with actual effort " + (0xffffffffL / (double) r) + " not as good as previous");
                }
            }
            if (bestSolution != null) {
                off = CHALLENGE_LEN;
                for (int j = 0; j < 8; j++) {
                    DataHelper.toLongLE(challenge, off, 2, bestSolution[j]);
                    off += 2;
                }
                // 40 byte proof: 16 byte nonce, 4 byte effort, 4 byte seed prefix, 16 byte solution
                byte[] rv = new byte[PROOF_LEN];
                System.arraycopy(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN, rv, 0, NONCE_LEN);
                off = NONCE_LEN;
                System.arraycopy(challenge, P.length + Hash.HASH_LENGTH + SEED_LEN + NONCE_LEN, rv, off, EFFORT_LEN);
                off += EFFORT_LEN;
                System.arraycopy(seed, 0, rv, off, SEED_PFX_LEN);
                off += SEED_PFX_LEN;
                System.arraycopy(challenge, CHALLENGE_LEN, rv, off, SOLUTION_LEN);
                System.out.println("Found proof of effort " + effort + " on run " + runs +
                                   " took " + (System.currentTimeMillis() - start) + " ms");
                return rv;
            }
            System.out.println("No solutions meet effort " + effort + " on run " + runs);
            incrementNonce(challenge);
        }
        return null;
    }

    /**
     *  Seed match and Bloom filter not done here
     *
     *  @param proof 40 bytes
     */
    public static boolean verify(HXCtx ctx, Hash hash, byte[] seed, int effort, byte[] proof) {
        long start = System.nanoTime();
        long claimedEffort = DataHelper.fromLong(proof, NONCE_LEN, 4);
        if (claimedEffort < effort) {
            System.out.println("Proof claimed effort " + claimedEffort + " does not meet required effort " + effort);
            return false;
        }
        // construct 116 byte check array
        byte[] check = new byte[CHECK_LEN];
        System.arraycopy(P, 0, check, 0, P.length);
        int off = P.length;
        System.arraycopy(hash.getData(), 0, check, off, Hash.HASH_LENGTH);
        off += Hash.HASH_LENGTH;
        System.arraycopy(seed, 0, check, off, SEED_LEN);
        off += SEED_LEN;
        System.arraycopy(proof, 0, check, off, NONCE_LEN + EFFORT_LEN);
        off += NONCE_LEN + EFFORT_LEN;
        if (!DataHelper.eq(proof, NONCE_LEN + EFFORT_LEN, seed, 0, SEED_PFX_LEN)) {
            System.out.println("Proof seed prefix mismatch");
            return false;
        }
        System.arraycopy(proof, NONCE_LEN + EFFORT_LEN + SEED_PFX_LEN, check, off, SOLUTION_LEN);
        //System.out.println("Verify:\n" + HexDump.dump(check));
        // blake2b-32 of the 116 byte check bytes
        MessageDigest blake = new Blake2bMessageDigest(null, null, BLAKE2B_32_LEN);
        blake.update(check, 0, CHECK_LEN);
        byte[] bhash = blake.digest();
        // r is the 4 byte check value
        long r = DataHelper.fromLong(bhash, 0, BLAKE2B_32_LEN);
        System.out.println("Actual effort: " + (0xffffffffL / r));
        long m = r * claimedEffort;
        if (m > 0xffffffffL) {
            System.out.println("Proof is less than claimed effort " + claimedEffort);
            return false;
        }
        if (claimedEffort > effort) {
            System.out.println("Proof effort " + claimedEffort + " is more than required effort " + effort);
        }
        char[] solution = new char[8];
        off = NONCE_LEN + EFFORT_LEN + SEED_PFX_LEN;
        for (int j = 0; j < 8; j++) {
            solution[j] = (char) DataHelper.fromLongLE(proof, off, 2);
            off += 2;
        }
        Result result = Equix.verify(ctx, check, CHALLENGE_LEN, solution);
        System.out.println("Verify result: " + result + " took " + ((System.nanoTime() - start) / 1000) + " us");
        return result == Result.OK;
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
        int effort = 8;
        if (args.length > 0)
            effort = Integer.parseInt(args[0]);
        HXCtx ctx = new HXCtx(512);
        byte[] seed = new byte[Hash.HASH_LENGTH];
        RandomSource.getInstance().nextBytes(seed);
        byte[] hash = new byte[Hash.HASH_LENGTH];
        RandomSource.getInstance().nextBytes(hash);
        Hash h = new Hash(hash);
        byte[] proof = solve(ctx, h, seed, effort, 999);
        if (proof != null) {
            System.out.println("Found solution:\n" + HexDump.dump(proof));
            // force regenerate w/o compile for test
            ctx.state = CompiledState.INIT;
            boolean ok = verify(ctx, h, seed, effort, proof);
            if (ok)
                System.out.println("Proof verify success");
            else
                System.out.println("Proof verify failed");
        } else {
            System.out.println("No solution found");
        }
    }

}
