package im.wangbo.bj58.ffmpeg.cli.ff.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ShowVersionArg implements FfArg {
    @Override
    public final String name() {
        return "-version";
    }

    @Override
    public final String description() {
        return "Show version";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static ShowVersionArg of() {
        return new AutoValue_ShowVersionArg();
    }
}
