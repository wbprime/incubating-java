package im.wangbo.bj58.ffmpeg.codec;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    String name();

    Optional<MediaEncoder> encoder();

    Optional<MediaDecoder> decoder();

    static MediaCodec copying() {
        return CopyingCodec.of();
    }
}
