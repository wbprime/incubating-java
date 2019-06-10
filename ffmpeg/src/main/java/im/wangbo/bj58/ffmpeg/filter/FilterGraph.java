package im.wangbo.bj58.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

import java.util.stream.Collectors;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FilterGraph extends Value {
    ImmutableList<FilterChain> chains();

    @Override
    default String encode() {
        return chains().stream().map(FilterChain::encode).collect(Collectors.joining(";"));
    }
}
