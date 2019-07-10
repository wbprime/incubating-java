package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.auto.value.AutoValue;
import java.util.Optional;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-10 by Elvis Wang
 */
@AutoValue
public abstract class Arg {

    abstract String name();

    abstract Optional<String> value();

    static Arg create(final String name, final Optional<String> value) {
        return new AutoValue_Arg(name, value);
    }

    public static Arg named(final String name) {
        return create(name, Optional.empty());
    }

    public static Arg paired(final String name, final String value) {
        return create(name, Optional.of(value));
    }
}
