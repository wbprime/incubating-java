package im.wangbo.bj58.ffmpeg.codec;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    Optional<MediaEncoder> encoder();

    Optional<MediaDecoder> decoder();

    static MediaCodec copying() {
        return CopyingCodec.of();
    }

    static MediaCodec opus() {
        return OpusCodec.of();
    }

    static MediaCodec h264() {
        return CopyingCodec.of();
    }

    static MediaCodec avc() {
        return CopyingCodec.of();
    }

    static MediaCodec h265() {
        return CopyingCodec.of();
    }

    static MediaCodec hevc() {
        return CopyingCodec.of();
    }
}
