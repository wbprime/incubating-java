package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.common.SizeInByte;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class OutputFileSizeLimitArg implements OutputArg {
    @Override
    public final String name() {
        return "-fs";
    }

    @Override
    public final String description() {
        return "Set the file size limit, expressed in bytes. " +
            "No further chunk of bytes is written after the limit is exceeded.";
    }

    abstract SizeInByte size();

    @Override
    public final Optional<String> value() {
        return Optional.of(size().asString());
    }

    public static OutputFileSizeLimitArg of(final SizeInByte size) {
        return new AutoValue_OutputFileSizeLimitArg(size);
    }
}
