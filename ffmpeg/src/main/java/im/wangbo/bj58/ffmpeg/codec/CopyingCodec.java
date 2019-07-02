package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class CopyingCodec implements MediaCodec {
    @Override
    public final String name() {
        return "Copy as is";
    }

    @Override
    public final MediaEncoder encoder() {
        return StdEncoder.of("copy");
    }

    @Override
    public final MediaDecoder decoder() {
        return StdDecoder.of("copy");
    }

    static CopyingCodec of() {
        return new AutoValue_CopyingCodec();
    }
}
