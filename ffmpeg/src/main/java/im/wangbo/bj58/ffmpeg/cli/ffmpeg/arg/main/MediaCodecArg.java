package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InOutputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.StreamSpecifier;
import im.wangbo.bj58.ffmpeg.codec.MediaCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MediaCodecArg implements InOutputArg {
    @Override
    public final String argName() {
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
    public final Optional<String> argValue() {
        return Optional.of(format());
    }

    private static MediaCodecArg create(final StreamSpecifier streamSpecifier, final String format) {
        return new AutoValue_MediaCodecArg(streamSpecifier, format);
    }

    public static InputArg asInput(final StreamSpecifier specifier, final MediaCodec c) {
        return create(specifier, c.decoder().decoderName());
    }

    public static OutputArg asOutput(final StreamSpecifier specifier, final MediaCodec c) {
        return create(specifier, c.encoder().encoderName());
    }
}
