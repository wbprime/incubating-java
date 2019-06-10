package im.wangbo.bj58.ffmpeg.filter;

import org.junit.jupiter.api.Test;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class FilterGraphTest {
    @Test
    void encode() {
        final FilterGraph graph = StdFilterGraph.of();

        final String result = graph.encode();
        System.out.println(result);
    }
}