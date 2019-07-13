package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer;

import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json.JsonBasedWriterFormat;
import im.wangbo.bj58.ffmpeg.common.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface WriterFormat {
    WriterMeta meta();

    ImmutableList<Arg> args();

    static WriterFormat json() {
        return JsonBasedWriterFormat.of();
    }
}
