package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.common.FrameRate;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.util.Optional;

/**
 * Set the number of audio channels.
 * <p>
 * For output streams it is set by default to the number of input audio channels.
 * For input streams this option only makes sense for audio grabbing devices and
 * raw demuxers and is mapped to the corresponding demuxer options.
 *
 * @author Elvis Wang
 * @see FrameRate
 */
@AutoValue
public abstract class AudioChannelNumberArg implements InOutputArg {
    @Override
    public final String name() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-ac" : "-ac:" + specifier;
    }

    @Override
    public final String description() {
        return "Set the number of audio channels. " +
            "For output streams it is set by default to the number of input audio channels. " +
            "For input streams this option only makes sense for audio grabbing devices and " +
            "raw demuxers and is mapped to the corresponding demuxer options.";
    }

    abstract StreamSpecifier streamSpecifier();

    abstract int channelNumber();

    @Override
    public final Optional<String> value() {
        return Optional.of(String.valueOf(channelNumber()));
    }

    private static AudioChannelNumberArg create(StreamSpecifier streamSpecifier, int val) {
        return new AutoValue_AudioChannelNumberArg(streamSpecifier, val);
    }

    public static InputArg asInput(final StreamSpecifier specifier, final int n) {
        return create(specifier, n);
    }

    public static OutputArg asOutput(final StreamSpecifier specifier, final int n) {
        return create(specifier, n);
    }
}
