package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class CopyMediaCodec implements MediaCodec {
    @Override
    public MediaEncoder encoder() {
        return MediaEncoder.named("copy");
    }

    @Override
    public MediaDecoder decoder() {
        return MediaDecoder.named("copy");
    }
}
