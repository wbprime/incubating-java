package im.wangbo.bj58.ffmpeg.cli.common.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.FfmpegArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.FfprobeArg;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class HideBannerArg implements FfmpegArg, FfprobeArg {
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
