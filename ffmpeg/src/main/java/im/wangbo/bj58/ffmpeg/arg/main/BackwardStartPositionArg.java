package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.InputArg;
import im.wangbo.bj58.ffmpeg.arg.SeekPosition;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class BackwardStartPositionArg implements InputArg {
    abstract SeekPosition position();

    @Override
    public final String argName() {
        return "-sseof";
    }

    @Override
    public final String description() {
        return "Like the -ss option but relative to the \"end of file\". That is negative values are earlier in the file, 0 is at EOF.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(position().asString());
    }

    static BackwardStartPositionArg of(final SeekPosition position) {
        return new AutoValue_BackwardStartPositionArg(position);
    }
}
