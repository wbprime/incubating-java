package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdDecoder implements MediaDecoder {
    @Override
    public abstract String decoderName();

    static StdDecoder of(final String name) {
        return new AutoValue_StdDecoder(name);
    }

    @Override
    public final String asString() {
        return decoderName();
    }
}
