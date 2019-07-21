package im.wangbo.bj58.ffmpeg.cli.ffprobe.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class WriterFormatArg implements FfprobeArg {

    public abstract WriterFormat writerFormat();

    @Override
    public String name() {
        return "-of";
    }

    @Override
    public String description() {
        return "Set the output printing format.";
    }

    @Override
    public Optional<String> value() {
        final String str = writerFormat().args()
            .collect(arg -> arg.name() + arg.value().map(v -> "=" + v).orElse(""))
            .makeString(":");
        return Optional.of(
            str.isEmpty() ? writerFormat().meta().kind() : writerFormat().meta().kind() + "=" + str
        );
    }

    public static WriterFormatArg of(final WriterFormat format) {
        return new AutoValue_WriterFormatArg(format);
    }
}
