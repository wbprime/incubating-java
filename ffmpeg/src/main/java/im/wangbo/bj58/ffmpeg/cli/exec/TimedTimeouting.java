package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.auto.value.AutoValue;

import java.time.Duration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
@AutoValue
abstract class TimedTimeouting implements CliProcessTimeoutingStrategy {
    abstract long d();

    @Override
    public OptionalLong millis() {
        return OptionalLong.of(d());
    }

    static TimedTimeouting of(final long duration, final TimeUnit unit) {
        return of(Duration.ofNanos(unit.toMillis(duration)));
    }

    static TimedTimeouting of(final Duration duration) {
        return of(Duration.ofNanos(duration.toMillis()));
    }

    private static TimedTimeouting create(final long millis) {
        return new AutoValue_TimedTimeouting(millis);
    }
}
