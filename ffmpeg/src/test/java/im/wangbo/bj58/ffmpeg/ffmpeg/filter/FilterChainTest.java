package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-02 by Elvis Wang
 */
@Disabled
class FilterChainTest {

    @Test
    void describeTo() {
        final FilterChain filter = new FilterChain(SourceFilterBuilder.builder().build("in"));
        filter.andThen(SourceFilterBuilder.builder().build("in"), "next");

        final StringBuilder sb = new StringBuilder();
        filter.describeTo(sb);
        final String result = sb.toString();

        Assertions.assertThat(result).isEqualTo("");
    }

}
