package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class NeverOverwritingArg implements GlobalArg {
    @Override
    public final String name() {
        return "-n";
    }

    @Override
    public final String description() {
        return "Do not overwrite output files, and exit immediately if a specified output file already exists";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static NeverOverwritingArg of() {
        return new AutoValue_NeverOverwritingArg();
    }
}
