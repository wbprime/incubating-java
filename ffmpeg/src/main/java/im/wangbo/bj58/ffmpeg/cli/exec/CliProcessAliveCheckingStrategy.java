package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.auto.value.AutoValue;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-07, by Elvis Wang
 */
@AutoValue
public abstract class CliProcessAliveCheckingStrategy {
    /**
     * @return duration in milliseconds
     */
    abstract long millis();

    private static CliProcessAliveCheckingStrategy create(long millis) {
        return new AutoValue_CliProcessAliveCheckingStrategy(millis);
    }

    public static CliProcessAliveCheckingStrategy ofMillis(final long millis) {
        return create(millis);
    }

    public static CliProcessAliveCheckingStrategy of(final long n, final TimeUnit unit) {
        return create(unit.toMillis(n));
    }

    public static CliProcessAliveCheckingStrategy of(final Duration duration) {
        return create(duration.toMillis());
    }
}
