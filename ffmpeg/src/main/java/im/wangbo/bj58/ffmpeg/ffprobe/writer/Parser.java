package im.wangbo.bj58.ffmpeg.ffprobe.writer;

import im.wangbo.bj58.ffmpeg.ffprobe.MediaMetaInfo;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Parser {
    MediaMetaInfo parse(final String str);
}
