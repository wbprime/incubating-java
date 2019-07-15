package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.codec.MediaDecoder;
import im.wangbo.bj58.ffmpeg.codec.MediaEncoder;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MediaCodecArg implements InOutputArg {
    @Override
    public final String name() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-c" : "-c:" + specifier;
    }

    @Override
    public final String description() {
        return "Select an encoder (when used before an output file) or " +
            "a decoder (when used before an input file) for one or more streams. " +
            "For each stream, the last matching c option is applied";
    }

    abstract StreamSpecifier streamSpecifier();

    abstract String format();

    @Override
    public final Optional<String> value() {
        return Optional.of(format());
    }

    private static MediaCodecArg create(final StreamSpecifier streamSpecifier, final String format) {
        return new AutoValue_MediaCodecArg(streamSpecifier, format);
    }

    public static InputArg asInput(final StreamSpecifier specifier, final MediaDecoder c) {
        return create(specifier, c.decoderName());
    }

    public static OutputArg asOutput(final StreamSpecifier specifier, final MediaEncoder c) {
        return create(specifier, c.encoderName());
    }
}
