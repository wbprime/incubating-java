package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SizeInByte implements Value {
    @Override
    public final String encode() {
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
        K {
            @Override
            long bytesPerUnit() {
                return N_PER_K;
            }
        },
        M {
            @Override
            long bytesPerUnit() {
                return N_PER_M;
            }
        },
        G {
            @Override
            long bytesPerUnit() {
                return N_PER_G;
            }
        },
        T {
            @Override
            long bytesPerUnit() {
                return N_PER_T;
            }
        },
        Z {
            @Override
            long bytesPerUnit() {
                return N_PER_Z;
            }
        },
        Ki {
            @Override
            long bytesPerUnit() {
                return N_PER_Ki;
            }
        },
        Mi {
            @Override
            long bytesPerUnit() {
                return N_PER_Mi;
            }
        },
        Gi {
            @Override
            long bytesPerUnit() {
                return N_PER_Gi;
            }
        },
        Ti {
            @Override
            long bytesPerUnit() {
                return N_PER_Ti;
            }
        },
        Zi {
            @Override
            long bytesPerUnit() {
                return N_PER_Zi;
            }
        },
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
