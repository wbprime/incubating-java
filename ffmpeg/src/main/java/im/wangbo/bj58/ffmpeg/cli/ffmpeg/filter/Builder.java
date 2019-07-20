package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
@SuppressWarnings("unchecked")
public abstract class Builder<BUILDER extends Builder<BUILDER>> {
    private final MutableList<String> in = Lists.mutable.withInitialCapacity(1);
    private final MutableList<String> out = Lists.mutable.withInitialCapacity(1);

    public final BUILDER incomings(final List<String> list) {
        this.in.addAll(list);
        return (BUILDER) this;
    }

    public final BUILDER firstIncoming(final String incoming) {
        return incomings(Collections.singletonList(incoming));
    }

    public final BUILDER secondIncoming(final String incoming) {
        return incomings(Collections.singletonList(incoming));
    }

    public final BUILDER outgoings(final List<String> list) {
        this.out.addAll(list);
        return (BUILDER) this;
    }

    public final BUILDER firstOutgoing(final String outgoing) {
        return outgoings(Collections.singletonList(outgoing));
    }

    public final BUILDER secondOutgoing(final String outgoing) {
        return outgoings(Collections.singletonList(outgoing));
    }

    protected abstract String filterName();

    protected abstract ImmutableList<FilterArg> filterArgs();

    /**
     * @return min incomings required, inclusively
     */
    protected int minIncomings() {
        return 0;
    }

    /**
     * @return max incomings required exclusively or {@link OptionalInt#empty()} if no limit
     */
    protected abstract OptionalInt maxIncomings();

    /**
     * @return min incomings required, inclusively
     */
    protected int minOutgoings() {
        return 0;
    }

    /**
     * @return max outgoings required exclusively or {@link OptionalInt#empty()} if no limit
     */
    protected abstract OptionalInt maxOutgoings();

    public final Filter build() {
        final ImmutableList<String> immutableIn = in.toImmutable();
        {
            final int cnt = immutableIn.size();
            if (cnt < minIncomings()) {
                throw new IllegalStateException("Incomings number should ge " +
                    minIncomings() + " but was" + cnt);
            }
            if (maxIncomings().isPresent() && cnt >= maxIncomings().getAsInt()) {
                throw new IllegalStateException("Incomings number should lt " +
                    maxIncomings().getAsInt() + " but was" + cnt);
            }
        }
        final ImmutableList<String> immutableOut = out.toImmutable();
        {
            final int cnt = immutableOut.size();
            if (cnt < minOutgoings()) {
                throw new IllegalStateException("Outgoings number should ge " +
                    minIncomings() + " but was" + cnt);
            }
            if (maxOutgoings().isPresent() && cnt >= maxOutgoings().getAsInt()) {
                throw new IllegalStateException("Outgoings number should lt " +
                    maxOutgoings().getAsInt() + " but was" + cnt);
            }
        }

        return Filter.of(filterName(), filterArgs(), immutableIn, immutableOut);
    }
}
