package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdDecoder implements MediaDecoder {
    abstract String decoderName();

    static StdDecoder of(String name) {
        return new AutoValue_StdDecoder(name);
    }

    @Override
    public final String encode() {
        return decoderName();
    }
}
