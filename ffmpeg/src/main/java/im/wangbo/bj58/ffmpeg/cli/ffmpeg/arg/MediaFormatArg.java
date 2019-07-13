package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MediaFormatArg implements InOutputArg {
    @Override
    public final String name() {
        return "-f";
    }

    @Override
    public final String description() {
        return "Force input or output file format. " +
            "The format is normally auto detected for input files and " +
            "guessed from the file extension for output files, " +
            "so this option is not needed in most cases";
    }

    abstract String format();

    @Override
    public final Optional<String> value() {
        return Optional.of(format());
    }

    private static MediaFormatArg create(final String f) {
        return new AutoValue_MediaFormatArg(f);
    }

    public static InputArg asInput(final MediaFormat f) {
        return create(f.demuxer().demuxerName());
    }

    public static OutputArg asOutput(final MediaFormat f) {
        return create(f.muxer().muxerName());
    }
}
