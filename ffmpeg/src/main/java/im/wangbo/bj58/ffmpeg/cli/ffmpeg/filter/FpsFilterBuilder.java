package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import im.wangbo.bj58.ffmpeg.common.FrameRate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-filters.html#fps-1">fps filter</a> for details.
 *
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
public final class FpsFilterBuilder extends FilterBuilder<FpsFilterBuilder> {
    public static FpsFilterBuilder of() {
        return new FpsFilterBuilder();
    }

    private Optional<FrameRate> fps = Optional.empty();

    public FpsFilterBuilder fps(final FrameRate fps) {
        this.fps = Optional.of(fps);
        return this;
    }

    @Override
    protected String filterName() {
        return "fps";
    }

    @Override
    protected ImmutableList<FilterArg> filterArgs() {
        final MutableList<FilterArg> args = Lists.mutable.withInitialCapacity(1);
        fps.ifPresent(n -> args.add(FilterArg.paired("fps", n.asString())));
        return args.toImmutable();
    }

    @Override
    protected OptionalInt maxIncomings() {
        return OptionalInt.of(2);
    }

    @Override
    protected OptionalInt maxOutgoings() {
        return OptionalInt.of(2);
    }
}
