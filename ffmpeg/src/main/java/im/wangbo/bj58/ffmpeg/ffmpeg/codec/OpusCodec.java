package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class OpusCodec implements MediaCodec {
    @Override
    public final String name() {
        return "Opus";
    }

    @Override
    public final MediaEncoder encoder() {
        return StdEncoder.of("libopus");
    }

    @Override
    public final MediaDecoder decoder() {
        return StdDecoder.of("libopus");
    }

    static OpusCodec of() {
        return new AutoValue_OpusCodec();
    }
}
