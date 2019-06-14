package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ForceOverwritingArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-y";
    }

    @Override
    public final String description() {
        return "Overwrite output files without asking";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static ForceOverwritingArg of() {
        return new AutoValue_ForceOverwritingArg();
    }
}
