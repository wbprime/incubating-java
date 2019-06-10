package im.wangbo.bj58.ffmpeg.filter;

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
    default String encode() {
        return filters().stream().map(FilterChain::encode).collect(Collectors.joining(","));
    }
}
