package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InOutputArg;
import im.wangbo.bj58.ffmpeg.common.SeekDuration;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class SeekingDurationArg implements InOutputArg {
    abstract SeekDuration duration();

    @Override
    public final String description() {
        return "When used as an input option, limit the duration of data read from the input file. " +
                "When used as an output option, stop writing the output after its duration reaches duration.";
    }

    @Override
    public final String argName(){
        return "-t";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(duration().asString());
    }

    static SeekingDurationArg of(final SeekDuration d) {
        return new AutoValue_SeekingDurationArg(d);
    }
}
