package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.common.SeekDuration;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SeekingDurationArg implements InOutputArg {
    abstract SeekDuration duration();

    @Override
    public final String description() {
        return "When used as an input option, limit the duration of data read from the input file. " +
            "When used as an output option, stop writing the output after its duration reaches duration.";
    }

    @Override
    public final String name() {
        return "-t";
    }

    @Override
    public final Optional<String> value() {
        return Optional.of(duration().asString());
    }

    static SeekingDurationArg of(final SeekDuration d) {
        return new AutoValue_SeekingDurationArg(d);
    }
}
