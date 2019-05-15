package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Name {
    public abstract String name();

    public static Name of(final String name) {
        return new AutoValue_Name(name);
    }

    @Override
    public final String toString() {
        return "Name {" + name() + "}";
    }
}
