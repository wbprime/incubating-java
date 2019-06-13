package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.ffmpeg.StreamSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class OutputQualityLimitArg implements OutputArg {
    @Override
    public final String argName() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-q" : "-q:" + specifier;
    }

    abstract StreamSpecifier streamSpecifier();

    abstract int quality();

    @Override
    public final String description() {
        return "Stop writing to the stream after frameCount frames.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(String.valueOf(quality()));
    }

    public static OutputQualityLimitArg of(final StreamSpecifier specifier, int quality) {
        return new AutoValue_OutputQualityLimitArg(specifier, quality);
    }
}
