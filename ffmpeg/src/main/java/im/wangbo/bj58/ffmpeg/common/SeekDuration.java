package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SeekDuration {
    abstract Duration duration();

    public final String asString() {
        return duration().getSeconds() + "." + (duration().getNano() / 1000_000);
    }

    public static SeekDuration of(final Duration duration) {
        return new AutoValue_SeekDuration(duration);
    }

    public static SeekDuration of(final long duration, final TimeUnit unit) {
        return of(Duration.ofNanos(unit.toNanos(duration)));
    }
}
