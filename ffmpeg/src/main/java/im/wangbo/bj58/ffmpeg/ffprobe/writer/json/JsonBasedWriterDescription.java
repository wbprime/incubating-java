package im.wangbo.bj58.ffmpeg.ffprobe.writer.json;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.ffprobe.writer.WriterDescription;
import im.wangbo.bj58.ffmpeg.ffprobe.writer.WriterMeta;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class JsonBasedWriterDescription implements WriterDescription {
    @Override
    public final WriterMeta meta() {
        return null;
    }

    @Override
    public final ImmutableList<Arg> args() {
        return null;
    }
}
