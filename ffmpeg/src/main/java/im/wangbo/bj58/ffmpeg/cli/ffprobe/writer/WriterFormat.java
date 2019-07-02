package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer;

import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.common.Arg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json.JsonBasedWriterFormat;
import im.wangbo.bj58.ffmpeg.common.Value;
import java.util.stream.Collectors;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface WriterFormat extends Value {
    WriterMeta meta();

    ImmutableList<Arg> args();

    @Override
    default String asString() {
        final String str = args().stream()
            .map(arg -> arg.spec().name() + arg.value().map(v -> "=" + v.asString()).orElse(""))
                .collect(Collectors.joining(":"));
        return str.isEmpty() ? meta().kind() : meta().kind() + "=" + str;
    }

    static WriterFormat json() {
        return JsonBasedWriterFormat.of();
    }
}
