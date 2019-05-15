package im.wangbo.bj58.ffmpeg.common;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public abstract class Value {
    public abstract String value();

    @Override
    public final String toString() {
        return "Value {" + value() + "}";
    }

    public static Value string(final String str) {
        return PlainValue.of(str);
    }

    public static Value duration(final long duration, final TimeUnit unit) {
        return duration(Duration.ofMillis(unit.toMillis(duration)));
    }

    public static Value duration(final Duration duration) {
        return DurationValue.of(duration);
    }
}
