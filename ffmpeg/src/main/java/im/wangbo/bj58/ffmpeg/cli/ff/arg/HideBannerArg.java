package im.wangbo.bj58.ffmpeg.cli.ff.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
@AutoValue
public abstract class HideBannerArg implements FfArg {
    @Override
    public final String name() {
        return "-hide_banner";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    @Override
    public String description() {
        return "Suppress printing banner";
    }

    public static HideBannerArg of() {
        return new AutoValue_HideBannerArg();
    }
}
