package im.wangbo.bj58.ffmpeg.ffprobe.writer;

import com.google.common.collect.ImmutableList;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface WriterDescription {
    WriterMeta meta();

    ImmutableList<Arg> args();
}
