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
        return new SeqNumberAsPidStrategy();
    }

    static CliPidGeneratingStrategy timestampsBased(final Clock clock) {
        return new TimeStampsAsPidStrategy(clock);
    }

    static CliPidGeneratingStrategy timestampsBased() {
        return timestampsBased(Clock.systemUTC());
    }
}
