package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    MediaEncoder encoder();
    MediaDecoder decoder();
}
