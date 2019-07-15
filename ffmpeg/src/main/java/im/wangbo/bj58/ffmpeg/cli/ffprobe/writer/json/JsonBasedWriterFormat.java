package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.cli.arg.Arg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterMeta;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class JsonBasedWriterFormat implements WriterFormat {
    @Override
    public final WriterMeta meta() {
        return JsonBasedWriterMeta.of();
    }

    @Override
    public final ImmutableList<Arg> args() {
        return ImmutableList.of(CompactArg.of());
    }

    public static JsonBasedWriterFormat of() {
        return new AutoValue_JsonBasedWriterFormat();
    }

    @AutoValue
    static abstract class CompactArg implements Arg {
        @Override
        public String name() {
            return "compact";
        }

        @Override
        public String description() {
            return "Print compact JSON or not";
        }

        @Override
        public Optional<String> value() {
            return Optional.of("1");
        }

        static CompactArg of() {
            return new AutoValue_JsonBasedWriterFormat_CompactArg();
        }
    }
}
