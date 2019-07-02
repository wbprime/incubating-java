package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


/**
 * TODO Details go here.
 *
 * Created at 2019-07-02 by Elvis Wang
 */
class FilterLinkTest {

    private static Stream<Arguments> args() {
        return Stream.of(
            Arguments.of("a", 3, "[a_0][a_1][a_2]"),
            Arguments.of("a", 2, "[a_0][a_1]"),
            Arguments.of("a", 1, "[a_0]"),
            Arguments.of("a", 0, ""),
            Arguments.of("", 3, "[_0][_1][_2]"),
            Arguments.of("", 2, "[_0][_1]"),
            Arguments.of("", 1, "[_0]"),
            Arguments.of("", 0, "")
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    void describeTo(final String name, final int count, final String expected) {
        final FilterLink link = FilterLink.of(name, count);

        final StringBuilder sb = new StringBuilder();
        link.describeTo(sb);
        final String result = sb.toString();

        Assertions.assertThat(result).isEqualTo(expected);
    }
}
