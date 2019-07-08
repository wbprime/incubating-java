package im.wangbo.bj58.ffmpeg.cli.exec;

import java.util.StringJoiner;
import java.util.UUID;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class UuidAsPidStrategy implements CliPidGeneratingStrategy {
    @Override
    public String get() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UuidAsPidStrategy.class.getSimpleName() + "[", "]")
            .toString();
    }
}
