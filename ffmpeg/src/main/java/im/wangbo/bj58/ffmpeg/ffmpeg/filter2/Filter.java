package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class Filter implements DescribeTo {

    public abstract String name();

    public abstract FilterSpec spec();

    public abstract ImmutableList<FilterArg> args();

    @Override
    public void describeTo(StringBuilder sb) {
        sb.append(spec().typeId());
        if (!args().isEmpty()) {
            sb.append('=');

            final String collect = args().stream()
                .map(arg -> arg.argName() + arg.argValue().map(v -> '=' + v).orElse(""))
                .collect(Collectors.joining(":"));
            sb.append(collect);
        }
    }

    public static Filter of(
        final String name, final FilterSpec spec, final List<FilterArg> args
    ) {
        return new AutoValue_Filter(name, spec, ImmutableList.copyOf(args));
    }

    interface Builder {

        Filter build(final String name);
    }
}
