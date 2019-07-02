package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer;

import com.google.common.collect.ImmutableList;

import java.util.stream.Collectors;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.Value;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json.JsonBasedWriterFormat;

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
        final String str = args().stream().map(arg -> arg.argName() + arg.argValue().map(v -> "=" + v).orElse(""))
                .collect(Collectors.joining(":"));
        return str.isEmpty() ? meta().kind() : meta().kind() + "=" + str;
    }

    static WriterFormat json() {
        return JsonBasedWriterFormat.of();
    }
}
