package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-filters.html#setpts_002c-asetpts">fps filter</a> for details.
 *
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class SetptsFilterBuilder extends FilterBuilder<SetptsFilterBuilder> {
    public static SetptsFilterBuilder of() {
        return new SetptsFilterBuilder();
    }

    private Optional<String> expr = Optional.empty();

    public SetptsFilterBuilder expr(final String expr) {
        this.expr = Optional.of(expr);
        return this;
    }

    @Override
    protected String filterName() {
        return "setpts";
    }

    @Override
    protected ImmutableList<FilterArg> filterArgs() {
        final MutableList<FilterArg> args = Lists.mutable.withInitialCapacity(1);
        expr.ifPresent(n -> args.add(FilterArg.paired("expr", n)));
        return args.toImmutable();
    }

    @Override
    protected OptionalInt maxIncomings() {
        return OptionalInt.of(1);
    }

    @Override
    protected OptionalInt maxOutgoings() {
        return OptionalInt.of(1);
    }
}
