package im.wangbo.bj58.ffmpeg.arg;

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
class SizeInByteTest {
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
    }

    @Test
    void convert() {
        for (final SizeInByte.Unit unit : SizeInByte.Unit.values()) {
            final long n = random.nextInt(Integer.MAX_VALUE) + 1;
            final long bytes = SizeInByte.Unit.B.convert(n, unit);
            Assertions.assertThat(bytes).isEqualTo(unit.bytesPerUnit() * n);
        }
    }

    @ParameterizedTest
    @MethodSource("args")
    void sizeInB(final SizeInByte.Unit unit, final long expectedN) {
        final long n = random.nextInt(Integer.MAX_VALUE) + 1;

        final long bytesPerUnit = unit.bytesPerUnit();
        final long bytes = unit.bytes(n);
        final long bits = unit.bits(n);

        Assertions.assertThat(bytesPerUnit).isEqualTo(expectedN);
        Assertions.assertThat(bytes).isEqualTo(expectedN * n);
        Assertions.assertThat(bits).isEqualTo(expectedN * n * 8L);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(SizeInByte.Unit.K, 1000L),
                Arguments.of(SizeInByte.Unit.M, 1000_000L),
                Arguments.of(SizeInByte.Unit.G, 1000_000_000L),
                Arguments.of(SizeInByte.Unit.T, 1000_000_000_000L),
                Arguments.of(SizeInByte.Unit.Z, 1000_000_000_000_000L),
                Arguments.of(SizeInByte.Unit.Ki, 1024L),
                Arguments.of(SizeInByte.Unit.Mi, 1024L * 1024L),
                Arguments.of(SizeInByte.Unit.Gi, 1024L * 1024L * 1024L),
                Arguments.of(SizeInByte.Unit.Ti, 1024L * 1024L * 1024L * 1024L),
                Arguments.of(SizeInByte.Unit.Zi, 1024L * 1024L * 1024L * 1024L * 1024L),
                Arguments.of(SizeInByte.Unit.B, 1L)
        );
    }
}