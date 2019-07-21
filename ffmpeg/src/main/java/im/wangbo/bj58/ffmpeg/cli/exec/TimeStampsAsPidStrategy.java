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
    private final String prefix;
    private final Clock clock;

    TimeStampsAsPidStrategy(final String prefix, final Clock clock) {
        this.prefix = prefix;
        this.clock = clock;
    }

    @Override
    public String get() {
        final Instant instant = clock.instant();
        return prefix + instant.getEpochSecond() + "_" + instant.getNano();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TimeStampsAsPidStrategy.class.getSimpleName() + "[", "]")
            .add("prefix='" + prefix + "'")
            .add("clock=" + clock)
            .toString();
    }
}
