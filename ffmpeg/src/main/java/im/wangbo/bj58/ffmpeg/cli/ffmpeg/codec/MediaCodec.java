package im.wangbo.bj58.ffmpeg.cli.ffmpeg.codec;

import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    String name();

    @Nullable
    MediaEncoder encoder();

    @Nullable
    MediaDecoder decoder();

    static MediaCodec copying() {
        return CopyingCodec.of();
    }
}
