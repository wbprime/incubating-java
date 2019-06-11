package im.wangbo.bj58.ffmpeg.ffmpeg.seek;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.SeekDuration;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class DurationArg implements Arg {
    abstract SeekDuration duration();

    @Override
    public final String argName(){
        return "-t";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(duration().asString());
    }

    static DurationArg of(final SeekDuration d) {
        return new AutoValue_DurationArg(d);
    }
}
