package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class OutputQualityLimitArg implements OutputArg {
    @Override
    public final String name() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-q" : "-q:" + specifier;
    }

    abstract StreamSpecifier streamSpecifier();

    abstract int quality();

    @Override
    public final String description() {
        return "Use fixed quality scale (VBR). The meaning of q/qscale is codec-dependent.";
    }

    @Override
    public final Optional<String> value() {
        return Optional.of(String.valueOf(quality()));
    }

    public static OutputQualityLimitArg of(final StreamSpecifier specifier, int quality) {
        return new AutoValue_OutputQualityLimitArg(specifier, quality);
    }
}
