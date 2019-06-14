package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.ffmpeg.StreamSpecifier;
import im.wangbo.bj58.ffmpeg.ffmpeg.filter.FilterChain;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SimpleFilterArg implements OutputArg {
    @Override
    public final String argName() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-filter" : "-filter:" + specifier;
    }

    abstract StreamSpecifier streamSpecifier();

    abstract FilterChain filterChain();

    @Override
    public final String description() {
        return "Stop writing to the stream after frameCount frames.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(filterChain().asString());
    }

    public static SimpleFilterArg of(final StreamSpecifier streamSpecifier, final FilterChain chain) {
        return new AutoValue_SimpleFilterArg(streamSpecifier, chain);
    }
}
