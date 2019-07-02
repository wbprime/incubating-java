package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.StreamSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class OutputFramesLimitArg implements OutputArg {
    @Override
    public final String argName() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-frames" : "-frames:" + specifier;
    }

    abstract StreamSpecifier streamSpecifier();

    abstract int frameCount();

    @Override
    public final String description() {
        return "Stop writing to the stream after frameCount frames.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(String.valueOf(frameCount()));
    }

    public static OutputFramesLimitArg of(final StreamSpecifier specifier, final int frameCount) {
        return new AutoValue_OutputFramesLimitArg(specifier, frameCount);
    }
}
