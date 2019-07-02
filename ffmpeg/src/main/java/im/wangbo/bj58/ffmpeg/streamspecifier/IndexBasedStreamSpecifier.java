package im.wangbo.bj58.ffmpeg.streamspecifier;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class IndexBasedStreamSpecifier implements StreamSpecifier {
    @Override
    public final String asString() {
        return String.valueOf(index());
    }

    abstract int index();

    static IndexBasedStreamSpecifier of(int index) {
        return new AutoValue_IndexBasedStreamSpecifier(index);
    }
}
