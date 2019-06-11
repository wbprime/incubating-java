package im.wangbo.bj58.ffmpeg.executor;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class PaddingSequenceNumberStrategy implements FilenameGeneratingStrategy {
    private final AtomicLong number;
    private final String prefix;

    PaddingSequenceNumberStrategy(final String prefix) {
        this.number = new AtomicLong(0L);
        this.prefix = prefix;
    }

    @Override
    public String apply(final String pid) {
        return prefix + "." + pid + "." + number.getAndIncrement();
    }
}
