package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Clock;
import java.time.Instant;
import java.util.StringJoiner;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class TimeStampsAsPidStrategy implements CliPidGeneratingStrategy {
    private final Clock clock;

    TimeStampsAsPidStrategy(final Clock clock) {
        this.clock = clock;
    }

    @Override
    public String get() {
        final Instant instant = clock.instant();
        return instant.getEpochSecond() + "_" + instant.getNano();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TimeStampsAsPidStrategy.class.getSimpleName() + "[", "]")
            .add("clock=" + clock)
            .toString();
    }
}
