package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Clock;
import java.util.function.Supplier;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-07, by Elvis Wang
 */
public interface CliPidGeneratingStrategy extends Supplier<String> {
    @Override
    String get();

    static CliPidGeneratingStrategy uuidBased() {
        return new UuidAsPidStrategy();
    }

    static CliPidGeneratingStrategy seqBased() {
        return seqBased("DEFAULT_");
    }
    static CliPidGeneratingStrategy seqBased(final String prefix) {
        return new SeqNumberAsPidStrategy(prefix);
    }

    static CliPidGeneratingStrategy timestampsBased(final String prefix, final Clock clock) {
        return new TimeStampsAsPidStrategy(prefix, clock);
    }

    static CliPidGeneratingStrategy timestampsBased(final String prefix) {
        return timestampsBased(prefix, Clock.systemUTC());
    }

    static CliPidGeneratingStrategy timestampsBased() {
        return timestampsBased("DEFAULT_");
    }
}
