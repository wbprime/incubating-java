package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Clock;
import java.util.function.Function;

/**
 * Strategy to generate a file name by pid.
 *
 * @author Elvis Wang
 */
public interface FilenameGeneratingStrategy extends Function<String, String> {
    /**
     * @param pid pid
     * @return new file name
     */
    @Override
    String apply(final String pid);

    public static FilenameGeneratingStrategy constant(final String name) {
        return new ConstantFilenameStrategy(name);
    }

    public static FilenameGeneratingStrategy paddingUuid(final String prefix) {
        return new PaddingUuidStrategy(prefix);
    }

    public static FilenameGeneratingStrategy paddingSequenceNumber(final String prefix) {
        return new PaddingSequenceNumberStrategy(prefix);
    }

    public static FilenameGeneratingStrategy paddingTimestamp(final String prefix, final Clock clock) {
        return new PaddingTimeStampsStrategy(prefix, clock);
    }
}
