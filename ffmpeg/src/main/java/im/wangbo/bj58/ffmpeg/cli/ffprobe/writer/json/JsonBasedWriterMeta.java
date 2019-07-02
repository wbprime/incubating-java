package im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.json;

import com.google.auto.value.AutoValue;

import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.Parser;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterMeta;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class JsonBasedWriterMeta implements WriterMeta {
    @Override
    public final String kind() {
        return "json";
    }

    @Override
    public Parser parser() {
        return new JsonBasedParser();
    }

    static JsonBasedWriterMeta of() {
        return new AutoValue_JsonBasedWriterMeta();
    }
}
