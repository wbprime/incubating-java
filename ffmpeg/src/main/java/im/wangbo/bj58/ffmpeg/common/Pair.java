package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Pair {
    public abstract Name name();
    public abstract Value value();

    public static Pair of(final Name name, final Value value) {
        return new AutoValue_Pair(name, value);
    }
}
