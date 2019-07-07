package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Clock;
import java.time.Instant;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class PaddingTimeStampsStrategy implements FilenameGeneratingStrategy {
    private final Clock clock;
    private final String prefix;

    PaddingTimeStampsStrategy(final String prefix, final Clock clock) {
        this.clock = clock;
        this.prefix = prefix;
    }

    @Override
    public String apply(final String pid) {
        final Instant instant = clock.instant();
        return prefix + "." + pid + "." + instant.getEpochSecond() + "_" + instant.getNano();
    }
}
