package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class StdFilterGraph implements FilterGraph {
    @Override
    public abstract ImmutableList<FilterChain> chains();

    public static StdFilterGraph of() {
        return new AutoValue_StdFilterGraph(ImmutableList.of());
    }

    public static StdFilterGraph of(final List<FilterChain> chains) {
        return new AutoValue_StdFilterGraph(ImmutableList.copyOf(chains));
    }
}
