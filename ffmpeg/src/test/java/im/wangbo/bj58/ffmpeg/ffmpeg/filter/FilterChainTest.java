package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import org.junit.jupiter.api.Test;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class FilterChainTest {
    @Test
    void encode() {
        final FilterChain chain = StdFilterChain.of();

        final String result = chain.asString();
        System.out.println(result);
    }

}