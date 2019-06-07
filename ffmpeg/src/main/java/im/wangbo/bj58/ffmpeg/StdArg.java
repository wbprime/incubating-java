package im.wangbo.bj58.ffmpeg;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class StdArg implements Arg {
    abstract List<String> opts();

    @Override
    public final List<String> encode() {
        return opts();
    }

    private static StdArg create(final List<String> opts) {
        return new AutoValue_StdArg(opts);
    }

    public static StdArg of(final String opt) {
        return create(ImmutableList.of(opt));
    }

    public static StdArg of(final String name, final String value) {
        return create(ImmutableList.of(name, value));
    }
}
