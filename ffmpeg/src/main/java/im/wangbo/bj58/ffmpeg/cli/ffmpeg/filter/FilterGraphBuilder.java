package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.List;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class FilterGraphBuilder {
    private final ImmutableList<FilterChain> chains;

    private final MutableList<Filter> curChain = Lists.mutable.empty();

    private FilterGraphBuilder(final ImmutableList<FilterChain> chains) {
        this.chains = chains;
    }

    public FilterGraphBuilder then(final Filter filter) {
        return this;
    }

    public FilterGraphBuilder next() {
        return new FilterGraphBuilder(chains.newWith(FilterChain.of(curChain.toImmutable().castToList())));
    }

    public ImmutableList<FilterChain> build() {
        return chains.newWith(FilterChain.of(curChain.toImmutable().castToList()));
    }

    @AutoValue
    public static abstract class FilterChain {
        public abstract List<Filter> filters();

        static FilterChain of(final List<Filter> filters) {
            return new AutoValue_FilterGraphBuilder_FilterChain(filters);
        }
    }
}
