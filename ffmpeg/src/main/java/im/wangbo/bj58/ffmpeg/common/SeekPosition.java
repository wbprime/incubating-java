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
public abstract class SeekPosition {
    /**
     * Minutes per hour.
     */
    private static final int MINUTES_PER_HOUR = 60;
    /**
     * Seconds per minute.
     */
    private static final int SECONDS_PER_MINUTE = 60;
    /**
     * Seconds per hour.
     */
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    /**
     * Nanos per second.
     */
    private static final long NANOS_PER_SECOND = 1000_000_000L;

    abstract Duration position();

    public final String asString() {
        final long s = position().getSeconds();
        final long n = position().getNano();

        final long hours = s / SECONDS_PER_HOUR;
        final int minutes = (int) ((s % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        final int secs = (int) (s % SECONDS_PER_MINUTE);

        final StringBuilder buf = new StringBuilder(24);
        if (hours != 0) {
            buf.append(hours).append(":");
        }
        buf.append(minutes).append(":").append(secs);
        if (n > 0L) {
            final int pos = buf.length();
            buf.append(n + NANOS_PER_SECOND);
            while (buf.charAt(buf.length() - 1) == '0') {
                buf.setLength(buf.length() - 1);
            }
            buf.setCharAt(pos, '.');
        }
        return buf.toString();
    }

    public static SeekDuration of(final Duration duration) {
        return new AutoValue_SeekDuration(duration);
    }

    public static SeekDuration of(final long duration, final TimeUnit unit) {
        return of(Duration.ofNanos(unit.toNanos(duration)));
    }
}
