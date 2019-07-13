package im.wangbo.bj58.ffmpeg.cli.ffprobe.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;

import java.util.Optional;
import java.util.stream.Collectors;

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
        final String str = writerFormat().args().stream()
            .map(arg -> arg.spec().name() + arg.value().map(v -> "=" + v.asString()).orElse(""))
            .collect(Collectors.joining(":"));
        return Optional.of(
            str.isEmpty() ? writerFormat().meta().kind() : writerFormat().meta().kind() + "=" + str
        );
    }

    public static WriterFormatArg of(final WriterFormat format) {
        return new AutoValue_WriterFormatArg(format);
    }
}
