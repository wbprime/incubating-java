package im.wangbo.bj58.ffmpeg.cli.exec;

import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class SeqNumberAsPidStrategy implements CliPidGeneratingStrategy {
    private final String prefix;
    private final AtomicLong id = new AtomicLong(0L);

    SeqNumberAsPidStrategy(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String get() {
        return prefix + id.getAndIncrement();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SeqNumberAsPidStrategy.class.getSimpleName() + "[", "]")
            .add("prefix='" + prefix + "'")
            .toString();
    }
}
