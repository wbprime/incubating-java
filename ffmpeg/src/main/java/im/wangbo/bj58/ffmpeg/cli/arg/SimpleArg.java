package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-14, by Elvis Wang
 */
@AutoValue
public abstract class SimpleArg implements Arg {
    @Override
    public abstract String name();

    @Override
    public abstract Optional<String> value();

    @Override
    public final String description() {
        return "";
    }

    private static SimpleArg create(final String name, final Optional<String> value) {
        return new AutoValue_SimpleArg(name, value);
    }

    public static SimpleArg named(final String name) {
        return create(name, Optional.empty());
    }

    public static SimpleArg paired(final String name, final String val) {
        return create(name, Optional.of(val));
    }
}
