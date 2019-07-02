package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.InOutputArg;
import im.wangbo.bj58.ffmpeg.common.SeekPosition;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class EndPositionArg implements InOutputArg {
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

    static EndPositionArg of(final SeekPosition position) {
        return new AutoValue_EndPositionArg(position);
    }
}
