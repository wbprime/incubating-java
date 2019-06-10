package im.wangbo.bj58.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

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
}
