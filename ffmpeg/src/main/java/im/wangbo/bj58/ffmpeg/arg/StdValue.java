package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdValue implements Value {
    @Override
    public abstract String asString();

    static StdValue of(final String value) {
        return new AutoValue_StdValue(value);
    }
}
