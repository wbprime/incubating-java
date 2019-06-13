package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdMediaCodec implements MediaCodec {
    abstract String encoderName();

    abstract String decoderName();

    @Override
    public final MediaEncoder encoder() {
        return StdEncoder.of(encoderName());
    }

    @Override
    public final MediaDecoder decoder() {
        return StdDecoder.of(decoderName());
    }

    static StdMediaCodec create(final String encoderName, final String decoderName) {
        return new AutoValue_StdMediaCodec(encoderName, decoderName);
    }
}
