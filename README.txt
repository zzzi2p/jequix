Java Equi-X
---------------------------

Tor-compatible HS PoW, Equi-X(60,3), and HashX.

References:

- https://spec.torproject.org/hspow-spec/index.html
- https://github.com/tevador/equix
- https://github.com/tevador/hashx

While PoW is well-specified, Equi-X and HashX are poorly documented,
and almost impossible to specify. All HashX program generation code,
including the deterministic RNG, must be reproduced exactly.
This is a fairly direct translation of tevador's C code to Java,
and is therefore licensed the same as that code, LGPLv3.

Equi-X and HashX test vectors are from those projects and all pass.

PoW 40 byte proof (without the leading 0x01) is identical to Tor's HS PoW.
PoW test vectors are from Tor and licensed the same as Tor (BSD).
One test is failing for unknown reasons.

Interpreted hash rate is about 150K hashes/sec, which is a little
slower than the C interpreted speed.

HashX compilation is supported if ant and ECJ are available at runtime.
This provides about a 10X speedup after the compilation.
The first compilation takes about 750 ms to load the ant and ECJ classes,
but subsequent compiles are about 50-100 ms.

Compiled hash rate is currently about 1.5 million hashes/sec, which is
about 2-3x slower than HashX compiled mode. Compilation is definitely
worth it for the solver, which runs 64K hashes per program.
Unfortunately, the compilation overhead makes this uncompetitive
with the C implementation.

Equi-X requires 1.8 MB of heap to find solutions.
This implementation uses about the same, plus lots of Java overhead of course.

Equi-X solving supports multi-threading within a single context.
Thread count must be a power of two. Multi-threading provides
speedups without increasing memory usage.
PoW is currently single-threaded, multi-threading (one for each seed) is not yet supported.
PoW multi-threading would require 1.8 MB of heap for each thread.
PoW verification is relatively fast, as in the C code.
As verification only requires 8 hashes (vs 64K hashes for each solution attempt),
verification does not benefit from compilation as the overhead is too great.


Blake2b code is adapted from Noise, see LICENSE-Noise.txt.

Build requires ant, Java 8 or higher, and i2p source in ../i2p.i2p.
Runtime requires Java 8 or higher and i2p.jar.
For optional compilation at runtime, ant and ECJ are required.
ECJ is now provided by eclipse-jdt: sudo apt install libeclipse-jdt-core-java,
the older libecj-java package is not recommended.
I2P dependency is only for a few utilities.
