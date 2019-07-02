package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdEncoder implements MediaEncoder {
    @Override
    public abstract String encoderName();

    static StdEncoder of(final String name) {
        return new AutoValue_StdEncoder(name);
    }
}
