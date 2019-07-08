package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StringValue implements Value {

    @Override
    public abstract String asString();

    static StringValue of(final String value) {
        return new AutoValue_StdValue(value);
    }
}
