package im.wangbo.bj58.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FilterGraph {
    ImmutableList<FilterChain> chains();
}
