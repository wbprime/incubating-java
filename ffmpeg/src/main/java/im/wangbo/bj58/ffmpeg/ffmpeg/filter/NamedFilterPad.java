package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class NamedFilterPad implements FilterPad {
    abstract String name();

    @Override
    public final String asString() {
        return "[" + name() + "]";
    }

    static NamedFilterPad of(final String name) {
        return new AutoValue_NamedFilterPad(name);
    }
}
