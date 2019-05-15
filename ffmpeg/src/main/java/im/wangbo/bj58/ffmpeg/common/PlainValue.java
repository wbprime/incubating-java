package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class PlainValue extends Value {
    public abstract String value();

    static PlainValue of(final String value) {
        return new AutoValue_PlainValue(value);
    }
}
