package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class IndexBasedStreamSpecifier implements StreamSpecifier {
    @Override
    public final String encode() {
        return String.valueOf(index());
    }

    abstract int index();

    static IndexBasedStreamSpecifier of(int index) {
        return new AutoValue_IndexBasedStreamSpecifier(index);
    }
}
