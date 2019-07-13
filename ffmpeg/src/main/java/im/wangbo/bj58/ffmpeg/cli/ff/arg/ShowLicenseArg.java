package im.wangbo.bj58.ffmpeg.cli.ff.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ShowLicenseArg implements FfArg {
    @Override
    public final String name() {
        return "-L";
    }

    @Override
    public final String description() {
        return "Show license";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static ShowLicenseArg of() {
        return new AutoValue_ShowLicenseArg();
    }
}
