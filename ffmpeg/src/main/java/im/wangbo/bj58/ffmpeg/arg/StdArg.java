package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdArg implements Arg {
    @Override
    public abstract String argName();

    @Override
    public abstract Optional<String> argValue();

    private static StdArg create(final String argName, final Optional<String> argValue) {
        return new AutoValue_StdArg(argName, argValue);
    }

    static StdArg of(final String opt) {
        return create(opt, Optional.empty());
    }

    static StdArg of(final String name, final String value) {
        return create(name, Optional.of(value));
    }
}
