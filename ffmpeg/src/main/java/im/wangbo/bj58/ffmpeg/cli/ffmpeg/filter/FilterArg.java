package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class FilterArg {
    public abstract String argName();

    public abstract Optional<String> argValue();

    private static FilterArg create(String argName, Optional<String> argValue) {
        return new AutoValue_FilterArg(argName, argValue);
    }

    public static FilterArg of(final String argName, final String argValue) {
        return create(argName, Optional.of(argValue));
    }

    public static FilterArg of(final String argName) {
        return create(argName, Optional.empty());
    }
}
