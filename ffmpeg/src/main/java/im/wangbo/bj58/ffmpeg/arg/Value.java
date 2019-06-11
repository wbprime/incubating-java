package im.wangbo.bj58.ffmpeg.arg;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Value {
    String asString();

    static Value of(final String value) {
        return StdValue.of(value);
    }

    static Value ofString(final String str) {
        return of(str);
    }

    static Value ofLong(final long l) {
        return of(String.valueOf(l));
    }

    static Value duration(final long duration, final TimeUnit unit) {
        return duration(Duration.ofNanos(unit.toNanos(duration)));
    }

    static Value duration(final Duration duration) {
        return ofString(duration.getSeconds() + "." + (duration.getNano() / 1000_000));
    }

    static Value datetime(final OffsetDateTime t) {
        return ofString(DateTimeFormatter.ISO_INSTANT.format(t));
    }

    static Value datetime(final LocalDateTime t) {
        return ofString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(t));
    }
}
