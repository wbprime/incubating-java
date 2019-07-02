package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.common;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ShowLicenseArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-L";
    }

    @Override
    public final String description() {
        return "Show license";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static ShowLicenseArg of() {
        return new AutoValue_ShowLicenseArg();
    }
}
