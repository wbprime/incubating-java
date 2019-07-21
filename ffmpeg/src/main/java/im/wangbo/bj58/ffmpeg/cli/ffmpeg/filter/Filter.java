package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import org.eclipse.collections.api.list.ImmutableList;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class Filter {

    public abstract String type();

    public abstract ImmutableList<FilterArg> args();

    public abstract ImmutableList<String> incomings();

    public abstract ImmutableList<String> outgoings();

    public static Filter of(final String type,
                            final ImmutableList<FilterArg> args,
                            final ImmutableList<String> incomings,
                            final ImmutableList<String> outgoings) {
        return new AutoValue_Filter(type, args, incomings, outgoings);
    }
}
