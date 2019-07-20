package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.OptionalInt;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class ScaleFilterBuilder extends Builder<ScaleFilterBuilder> {
    private final MutableList<String> in = Lists.mutable.withInitialCapacity(1);
    private final MutableList<String> out = Lists.mutable.withInitialCapacity(1);

    private OptionalInt w = OptionalInt.empty();
    private OptionalInt h = OptionalInt.empty();

    public ScaleFilterBuilder width(final int v) {
        this.w = OptionalInt.of(v);
        return this;
    }

    public ScaleFilterBuilder height(final int v) {
        this.h = OptionalInt.of(v);
        return this;
    }

    @Override
    protected String filterName() {
        return "scale";
    }

    @Override
    protected ImmutableList<FilterArg> filterArgs() {
        final MutableList<FilterArg> args = Lists.mutable.withInitialCapacity(2);
        w.ifPresent(n -> args.add(FilterArg.paired("w", String.valueOf(n))));
        h.ifPresent(n -> args.add(FilterArg.paired("h", String.valueOf(n))));
        return args.toImmutable();
    }

    @Override
    protected int minIncomings() {
        return 1;
    }

    @Override
    protected OptionalInt maxIncomings() {
        return OptionalInt.of(2);
    }

    @Override
    protected int minOutgoings() {
        return 1;
    }

    @Override
    protected OptionalInt maxOutgoings() {
        return OptionalInt.of(2);
    }
}
