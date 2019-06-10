package im.wangbo.bj58.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class StdFilterChain implements FilterChain {
    public abstract ImmutableList<Filter> filters();

    public static StdFilterChain of() {
        return new AutoValue_StdFilterChain(ImmutableList.of());
    }

    public static StdFilterChain of(final List<Filter> filters) {
        return new AutoValue_StdFilterChain(ImmutableList.copyOf(filters));
    }
}
