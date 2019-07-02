package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ArgSpec {
    public abstract String name();

    public abstract String description();

    public static ArgSpec of(final String name, final String description) {
        return new AutoValue_ArgSpec(name, description);
    }
}
