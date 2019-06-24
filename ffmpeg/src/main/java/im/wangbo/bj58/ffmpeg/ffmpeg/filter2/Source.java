package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.auto.value.AutoValue;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class Source implements Node {
    public abstract String name();

    public static Source of(final String name) {
        return new AutoValue_Source(name);
    }
}
