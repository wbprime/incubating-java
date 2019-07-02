package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InOutputArg;
import im.wangbo.bj58.ffmpeg.common.SeekPosition;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SeekingEndArg implements InOutputArg {

    abstract SeekPosition position();

    @Override
    public final String description() {
        return "Stop writing the output or reading the input at position.";
    }

    @Override
    public final String argName() {
        return "-to";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(position().asString());
    }

    public static SeekingEndArg of(final SeekPosition position) {
        return new AutoValue_SeekingEndArg(position);
    }
}
