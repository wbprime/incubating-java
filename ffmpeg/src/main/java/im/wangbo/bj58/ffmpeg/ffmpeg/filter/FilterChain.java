package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

import java.util.stream.Collectors;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FilterChain extends FilterGraph {
    ImmutableList<Filter> filters();

    @Override
    default ImmutableList<FilterChain> chains() {
        return ImmutableList.of(this);
    }

    @Override
    default String asString() {
        return filters().stream().map(FilterChain::asString).collect(Collectors.joining(","));
    }
}
