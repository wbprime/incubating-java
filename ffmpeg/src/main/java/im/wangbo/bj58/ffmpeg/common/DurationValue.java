package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

import java.time.Duration;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class DurationValue extends Value {
    public final String value() {
        return duration().getSeconds() + "s";
    }

    abstract Duration duration();

    static DurationValue of(final Duration duration) {
        return new AutoValue_DurationValue(duration);
    }
}
