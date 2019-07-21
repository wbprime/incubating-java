package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.Arg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterMeta;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.impl.factory.Lists;

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
        return Lists.immutable.with(CompactArg.of());
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
