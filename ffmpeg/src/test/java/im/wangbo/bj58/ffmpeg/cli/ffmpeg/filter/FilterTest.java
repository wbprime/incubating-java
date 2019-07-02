package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * TODO Details go here.
 *
 * Created at 2019-07-02 by Elvis Wang
 */
@Disabled
class FilterTest {

    @Test
    void describeTo() {
        final Filter filter = SourceFilterBuilder.builder().build("in");

        final StringBuilder sb = new StringBuilder();
        filter.describeTo(sb);
        final String result = sb.toString();

        Assertions.assertThat(result).isEqualTo("");
    }
}
