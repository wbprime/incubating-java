package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SizeInByte {
    public final String asString() {
        return String.valueOf(bytes());
    }

    public abstract long bytes();

    public final long bits() {
        return Unit.B.bits(bytes());
    }

    public static SizeInByte of(final long bytes) {
        return new AutoValue_SizeInByte(bytes);
    }

    public static SizeInByte of(final long n, final Unit unit) {
        return of(unit.bytes(n));
    }

    /**
     * TODO add brief description here
     *
     * @author Elvis Wang
     */
    public enum Unit {
        // 1000 Bytes
        K {
            @Override
            long bytesPerUnit() {
                return N_PER_K;
            }
        },
        // 1000 x 1000 Bytes
        M {
            @Override
            long bytesPerUnit() {
                return N_PER_M;
            }
        },
        // 1000 x 1000 x 1000 Bytes
        G {
            @Override
            long bytesPerUnit() {
                return N_PER_G;
            }
        },
        // 1000 x 1000 x 1000 x 1000 Bytes
        T {
            @Override
            long bytesPerUnit() {
                return N_PER_T;
            }
        },
        // 1000 x 1000 x 1000 x 1000 x 1000 Bytes
        Z {
            @Override
            long bytesPerUnit() {
                return N_PER_Z;
            }
        },
        // 1024 Bytes
        Ki {
            @Override
            long bytesPerUnit() {
                return N_PER_Ki;
            }
        },
        // 1024 x 1024 Bytes
        Mi {
            @Override
            long bytesPerUnit() {
                return N_PER_Mi;
            }
        },
        // 1024 x 1024 x 1024 Bytes
        Gi {
            @Override
            long bytesPerUnit() {
                return N_PER_Gi;
            }
        },
        // 1024 x 1024 x 1024 x 1024 Bytes
        Ti {
            @Override
            long bytesPerUnit() {
                return N_PER_Ti;
            }
        },
        // 1024 x 1024 x 1024 x 1024 x 1024 Bytes
        Zi {
            @Override
            long bytesPerUnit() {
                return N_PER_Zi;
            }
        },
        // 1 Byte
        B {
            @Override
            long bytesPerUnit() {
                return ONE_BYTE;
            }
        },
        ;

        private static long N = 1000L;
        private static long Ni = 1024L;

        private static long BITS_PER_BYTE = 8L;
        private static long ONE_BYTE = 1L;

        private static long N_PER_K = ONE_BYTE * N;
        private static long N_PER_M = N_PER_K * N;
        private static long N_PER_G = N_PER_M * N;
        private static long N_PER_T = N_PER_G * N;
        private static long N_PER_Z = N_PER_T * N;
        private static long N_PER_Ki = ONE_BYTE * Ni;
        private static long N_PER_Mi = N_PER_Ki * Ni;
        private static long N_PER_Gi = N_PER_Mi * Ni;
        private static long N_PER_Ti = N_PER_Gi * Ni;
        private static long N_PER_Zi = N_PER_Ti * Ni;

        abstract long bytesPerUnit();

        public final long bytes(final long n) {
            return n * bytesPerUnit();
        }

        public final long bits(final long n) {
            return bytes(n) * BITS_PER_BYTE;
        }

        public final long convert(final long n, final Unit unit) {
            return unit.bytes(n) / bytesPerUnit();
        }
    }
}
