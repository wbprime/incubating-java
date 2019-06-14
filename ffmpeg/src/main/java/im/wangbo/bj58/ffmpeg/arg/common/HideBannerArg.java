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
public abstract class HideBannerArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-hide_banner";
    }

    @Override
    public final String description() {
        return "Suppress printing banner";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static HideBannerArg of() {
        return new AutoValue_HideBannerArg();
    }
}
