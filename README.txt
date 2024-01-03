Java Equi-X
---------------------------

Tor-compatible PoW, Equi-X(60,3), and HashX.

References:

- https://spec.torproject.org/hspow-spec/index.html
- https://github.com/tevador/equix
- https://github.com/tevador/hashx

While PoW is well-specified, Equi-X and HashX are poorly undocumented,
and almost impossible to specify. All HashX program generation code,
including the deterministic RNG, must be reproduced exactly.
This is a fairly direct translation of tevador's C code to Java,
and is therefore licensed the same as that code, LGPLv3.

Equi-X and HashX test vectors are from those projects and all pass.

PoW test vectors are from Tor and licensed the same as Tor (BSD).
One test is failing for unknown reasons.

Interpreted hash rate is about 150K hashes/sec, which is a little
slower than the C interpreted speed.

HashX compilation is supported if ant and ECJ are available at runtime.
This provides about a 10X speedup after the compilation,
which takes about 750 ms.

Compiled hash rate is currently about 1.5 million hashes/sec, which is
about 2-3x slower than HashX compiled mode.

Equi-X requires 1.8 MB of heap to find solutions.
This implementation uses about the same, plus lots of Java overhead of course.

Blake2b code is adapted from Noise, see LICENSE-Noise.txt.
