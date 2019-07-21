package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class FilterGraphBuilder {
    private final ImmutableList<FilterChain> chains;

    private final MutableList<Filter> curChain = Lists.mutable.empty();

    public static FilterGraphBuilder of() {
        return new FilterGraphBuilder(Lists.immutable.empty());
    }

    private FilterGraphBuilder(final ImmutableList<FilterChain> chains) {
        this.chains = chains;
    }

    public FilterGraphBuilder then(final Filter filter) {
        return this;
    }

    public FilterGraphBuilder next() {
        return new FilterGraphBuilder(chains.newWith(FilterChain.of(curChain.toImmutable())));
    }

    public FilterGraph build() {
        return FilterGraph.of(chains.newWith(FilterChain.of(curChain.toImmutable())));
    }
}
