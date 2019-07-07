package im.wangbo.bj58.ffmpeg.cli.exec;

import java.util.UUID;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class PaddingUuidStrategy implements FilenameGeneratingStrategy {
    private final String prefix;

    PaddingUuidStrategy(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String apply(final String pid) {
        final UUID uuid = UUID.randomUUID();
        return prefix + "." + pid + "." + uuid.toString();
    }
}
