package im.wangbo.bj58.ffmpeg.arg.common;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ShowVersionArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-version";
    }

    @Override
    public final String description() {
        return "Show version";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static ShowVersionArg of() {
        return new AutoValue_ShowVersionArg();
    }
}
