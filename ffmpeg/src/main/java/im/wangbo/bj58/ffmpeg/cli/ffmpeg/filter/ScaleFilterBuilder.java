package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import im.wangbo.bj58.ffmpeg.common.SizeInPixel;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-filters.html#scale-1">scale filter</a> for details.
 *
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class ScaleFilterBuilder extends FilterBuilder<ScaleFilterBuilder> {
    private Optional<String> w = Optional.empty();
    private Optional<String> h = Optional.empty();

    public static ScaleFilterBuilder of() {
        return new ScaleFilterBuilder();
    }

    public ScaleFilterBuilder width(final int v) {
        this.w = Optional.of(String.valueOf(v));
        return this;
    }

    public ScaleFilterBuilder height(final int v) {
        this.h = Optional.of(String.valueOf(v));
        return this;
    }

    public ScaleFilterBuilder width(final String expr) {
        this.w = Optional.of(expr);
        return this;
    }

    public ScaleFilterBuilder height(final String expr) {
        this.h = Optional.of(expr);
        return this;
    }

    public ScaleFilterBuilder size(final SizeInPixel size) {
        return width(size.w()).height(size.h());
    }

    @Override
    protected String filterName() {
        return "scale";
    }

    @Override
    protected ImmutableList<FilterArg> filterArgs() {
        final MutableList<FilterArg> args = Lists.mutable.withInitialCapacity(2);
        w.ifPresent(n -> args.add(FilterArg.paired("w", n)));
        h.ifPresent(n -> args.add(FilterArg.paired("h", n)));
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
