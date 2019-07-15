package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.common.FrameRate;
import im.wangbo.bj58.ffmpeg.common.SizeInPixel;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.util.Optional;

/**
 * Set frame size
 *
 * @author Elvis Wang
 * @see FrameRate
 */
@AutoValue
public abstract class FrameSizeArg implements InOutputArg {
    @Override
    public final String name() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-s" : "-s:" + specifier;
    }

    @Override
    public final String description() {
        return "Set frame size. " +
            "As an input option, this is a shortcut for the video_size private option, " +
            "recognized by some demuxers for which the frame size is either not stored " +
            "in the file or is configurable – e.g. raw video or video grabbers." +
            "As an output option, this inserts the scale video filter to the end of the corresponding filtergraph." +
            "default - same as source.";
    }

    abstract StreamSpecifier streamSpecifier();

    abstract SizeInPixel sizeInPixel();

    @Override
    public final Optional<String> value() {
        return Optional.of(sizeInPixel().w() + "x" + sizeInPixel().h());
    }

    private static FrameSizeArg create(final StreamSpecifier specifier, final SizeInPixel size) {
        return new AutoValue_FrameSizeArg(specifier, size);
    }

    public static InputArg asInput(final StreamSpecifier specifier, final SizeInPixel size) {
        return create(specifier, size);
    }

    public static OutputArg asOutput(final StreamSpecifier specifier, final SizeInPixel size) {
        return create(specifier, size);
    }
}
