package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class WriterFormatArg implements FfprobeArg {
    abstract WriterFormat writerFormat();

    @Override
    public final String argName() {
        return "-of";
    }

    @Override
    public final String description() {
        return "Set the output printing format.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(writerFormat().asString());
    }

    public static WriterFormatArg of(final WriterFormat format) {
        return new AutoValue_WriterFormatArg(format);
    }
}
