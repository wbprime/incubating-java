package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import org.eclipse.collections.api.list.ImmutableList;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
@AutoValue
public abstract class FilterGraph {
    public abstract ImmutableList<FilterChain> chains();

    public static FilterGraph of(final ImmutableList<FilterChain> chains) {
        return new AutoValue_FilterGraph(chains);
    }
}
