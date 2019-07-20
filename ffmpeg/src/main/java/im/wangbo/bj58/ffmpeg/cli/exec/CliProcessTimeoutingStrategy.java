package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Duration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-07, by Elvis Wang
 */
public interface CliProcessTimeoutingStrategy {
    /**
     * Determine whether timeout a running process.
     *
     * <p>{@link OptionalLong#empty()} means no timeout at all.</p>
     *
     * @return duration in milliseconds
     */
    OptionalLong millis();

    static CliProcessTimeoutingStrategy unlimited() {
        return NoTimeouting.of();
    }

    static CliProcessTimeoutingStrategy limited(final long n, final TimeUnit unit) {
        return TimedTimeouting.of(n, unit);
    }

    static CliProcessTimeoutingStrategy limited(final Duration duration) {
        return TimedTimeouting.of(duration);
    }
}
