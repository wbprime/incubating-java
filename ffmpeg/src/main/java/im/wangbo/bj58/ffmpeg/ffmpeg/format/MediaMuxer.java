package im.wangbo.bj58.ffmpeg.ffmpeg.format;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaMuxer extends Value {
    String muxerName();

    @Override
    String asString();
}
