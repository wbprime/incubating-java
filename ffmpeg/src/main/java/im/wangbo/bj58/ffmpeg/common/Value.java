package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

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
@AutoValue
public abstract class Value {
    public abstract String value();

    public static Value of(final String value) {
        return new AutoValue_Value(value);
    }

    public static Value string(final String str) {
        return of(str);
    }

    public static Value duration(final long duration, final TimeUnit unit) {
        return duration(Duration.ofNanos(unit.toNanos(duration)));
    }

    public static Value duration(final Duration duration) {
        return string(duration.getSeconds() + "." + (duration.getNano() / 1000_000));
    }

    public static Value datetime(final OffsetDateTime t) {
        return string(DateTimeFormatter.ISO_INSTANT.format(t));
    }

    public static Value datetime(final LocalDateTime t) {
        return string(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(t));
    }

    public static Value bytes(final long n, final BinarySizeUnit unit) {
        final long bytes = BinarySizeUnit.B.convert(n, unit);
        return string(String.valueOf(bytes));
    }

    public static Value size(final int w, final int h) {
        return string(w + "x" + h);
    }

    public static Value size(final SizeInPixel size) {
        return size(size.width(), size.height());
    }

    public static Value size(final SizeInPixel.VideoSize size) {
        return size(size.sizeInPixel());
    }
}
