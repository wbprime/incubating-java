package im.wangbo.bj58.ffmpeg.ffmpeg.seek;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.SeekPosition;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class ForwardStartPositionArg implements Arg {
    abstract SeekPosition position();

    @Override
    public final String argName() {
        return "-ss";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(position().asString());
    }

    static ForwardStartPositionArg of(final SeekPosition position) {
        return new AutoValue_ForwardStartPositionArg(position);
    }
}
