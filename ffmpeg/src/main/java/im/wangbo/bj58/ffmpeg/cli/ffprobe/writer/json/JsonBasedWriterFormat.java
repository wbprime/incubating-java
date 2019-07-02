package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterMeta;
import im.wangbo.bj58.ffmpeg.common.Value;
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
        public ArgSpec spec() {
            return ArgSpec.of(
                "compact", "Print compact JSON or not"
            );
        }

        @Override
        public Optional<Value> value() {
            return Optional.of(Value.ofInt(1));
        }

        static CompactArg of() {
            return new AutoValue_JsonBasedWriterFormat_CompactArg();
        }
    }
}
