package im.wangbo.bj58.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Filter extends FilterChain {
    String typeId();

    ImmutableList<FilterPad> inputs();

    ImmutableList<FilterPad> outputs();

    List<Arg> args();

    @Override
    default ImmutableList<Filter> filters() {
        return ImmutableList.of(this);
    }

    @Override
    default String encode() {
        final StringBuilder sb = new StringBuilder();

        inputs().forEach(p -> sb.append(p.encode()));

        sb.append(typeId());

        if (! args().isEmpty()) {
            sb.append("=");
            final String collect = args().stream()
                    .map(a -> a.argValue().map(str -> a.argName() + "=" + str).orElse(a.argName()))
                    .collect(Collectors.joining(":"));
            sb.append(collect);
        }

        outputs().forEach(p -> sb.append(p.encode()));

        return sb.toString();
    }

    interface FilterBuilder {
        Filter build();
    }
}
