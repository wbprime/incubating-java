package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class CopyMediaCodec implements MediaCodec {
    @Override
    public MediaEncoder encoder() {
        return StdEncoder.of("copy");
    }

    @Override
    public MediaDecoder decoder() {
        return StdDecoder.of("copy");
    }
}
