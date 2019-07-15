package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MetadataArg implements OutputArg {
    @Override
    public final String name() {
        return "-metadata";
    }

    @Override
    public final String description() {
        return "Set a metadata key/value pair.";
    }

    abstract String key();

    abstract String val();

    @Override
    public final Optional<String> value() {
        return Optional.of(key() + "=" + val());
    }

    public static MetadataArg of(final String key, final String value) {
        return new AutoValue_MetadataArg(key, value);
    }
}
