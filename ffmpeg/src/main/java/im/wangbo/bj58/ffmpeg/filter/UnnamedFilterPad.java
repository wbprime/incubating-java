package im.wangbo.bj58.ffmpeg.filter;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class UnnamedFilterPad implements FilterPad {
    @Override
    public final String encode() {
        return "";
    }

    static UnnamedFilterPad of() {
        return new AutoValue_UnnamedFilterPad();
    }
}
