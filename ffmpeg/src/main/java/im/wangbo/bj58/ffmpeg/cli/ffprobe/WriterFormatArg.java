package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.arg.FfprobeArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import im.wangbo.bj58.ffmpeg.common.Value;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class WriterFormatArg implements FfprobeArg {

    abstract WriterFormat writerFormat();

    @Override
    public final ArgSpec spec() {
        return ArgSpec.of(
            "-of", "Set the output printing format."
        );
    }

    @Override
    public Optional<Value> value() {
        return Optional.of(writerFormat());
    }

    public static WriterFormatArg of(final WriterFormat format) {
        return new AutoValue_WriterFormatArg(format);
    }
}
