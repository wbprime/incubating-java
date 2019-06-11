package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.ffmpeg.StreamSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class FilterTest {
    @ParameterizedTest
    @MethodSource("args")
    void encode(final Filter filter, final String expected) {
        final String result = filter.asString();

        Assertions.assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(
                        StdFilter.builder("amerge")
                                .addArg(Arg.paired("inputs", "6"))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(1)))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(2)))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(3)))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(4)))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(5)))
                                .addInput(FilterPad.streamSpecifier(0, StreamSpecifier.of(6)))
                                .addOutput(FilterPad.unnamed())
                                .build(),
                        "[0:1][0:2][0:3][0:4][0:5][0:6]amerge=inputs=6"),
                Arguments.of(
                        StdFilter.builder("amovie")
                                .addArg(Arg.named("left.wav"))
                                .addInput(FilterPad.unnamed())
                                .addOutput(FilterPad.named("l"))
                                .build(),
                        "amovie=left.wav[l]"),
                Arguments.of(
                        StdFilter.builder("acrossfade")
                                .addArg(Arg.paired("d", "10"))
                                .addArg(Arg.paired("c1", "exp"))
                                .addArg(Arg.paired("c2", "exp"))
                                .addInput(FilterPad.unnamed())
                                .addOutput(FilterPad.unnamed())
                                .build(),
                        "acrossfade=d=10:c1=exp:c2=exp")
        );
    }
}