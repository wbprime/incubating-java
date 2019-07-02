package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class NeverOverwritingArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-n";
    }

    @Override
    public final String description() {
        return "Do not overwrite output files, and exit immediately if a specified output file already exists";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static NeverOverwritingArg of() {
        return new AutoValue_NeverOverwritingArg();
    }
}
