package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdEncoder implements MediaEncoder {
    abstract String encoderName();

    static StdEncoder of(String name) {
        return new AutoValue_StdEncoder(name);
    }

    @Override
    public final String asString() {
        return encoderName();
    }
}
