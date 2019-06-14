package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class FilterArg implements Arg {
    @Override
    public abstract String argName();

    @Override
    public abstract Optional<String> argValue();

    private static FilterArg create(final String argName, final Optional<String> argValue) {
        return new AutoValue_FilterArg(argName, argValue);
    }

    public static FilterArg named(final String opt) {
        return create(opt, Optional.empty());
    }

    public static FilterArg paired(final String name, final String value) {
        return create(name, Optional.of(value));
    }
}
