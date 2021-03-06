package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ForceOverwritingArg implements GlobalArg {
    @Override
    public final String name() {
        return "-y";
    }

    @Override
    public final String description() {
        return "Overwrite output files without asking";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static ForceOverwritingArg of() {
        return new AutoValue_ForceOverwritingArg();
    }
}
