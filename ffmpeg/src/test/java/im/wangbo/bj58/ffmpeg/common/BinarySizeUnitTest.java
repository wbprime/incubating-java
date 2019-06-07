package im.wangbo.bj58.ffmpeg.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class BinarySizeUnitTest {
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    @Test
    void convert() {
        for (final BinarySizeUnit unit : BinarySizeUnit.values()) {
            final long n = random.nextInt(Integer.MAX_VALUE) + 1;
            final long bytes = BinarySizeUnit.B.convert(n, unit);
            Assertions.assertThat(bytes).isEqualTo(unit.sizeInBytes() * n);
        }
    }

    @ParameterizedTest
    @MethodSource("args")
    void sizeInB(final BinarySizeUnit unit, final long expectedN) {
        final long n = random.nextInt(Integer.MAX_VALUE) + 1;

        final long bytesPerUnit = unit.sizeInBytes();
        final long bytes = unit.bytes(n);
        final long bits = unit.bits(n);

        Assertions.assertThat(bytesPerUnit).isEqualTo(expectedN);
        Assertions.assertThat(bytes).isEqualTo(expectedN * n);
        Assertions.assertThat(bits).isEqualTo(expectedN * n * 8L);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(BinarySizeUnit.K, 1000L),
                Arguments.of(BinarySizeUnit.M, 1000_000L),
                Arguments.of(BinarySizeUnit.G, 1000_000_000L),
                Arguments.of(BinarySizeUnit.T, 1000_000_000_000L),
                Arguments.of(BinarySizeUnit.Z, 1000_000_000_000_000L),
                Arguments.of(BinarySizeUnit.Ki, 1024L),
                Arguments.of(BinarySizeUnit.Mi, 1024L * 1024L),
                Arguments.of(BinarySizeUnit.Gi, 1024L * 1024L * 1024L),
                Arguments.of(BinarySizeUnit.Ti, 1024L * 1024L * 1024L * 1024L),
                Arguments.of(BinarySizeUnit.Zi, 1024L * 1024L * 1024L * 1024L * 1024L),
                Arguments.of(BinarySizeUnit.B, 1L)
        );
    }
}