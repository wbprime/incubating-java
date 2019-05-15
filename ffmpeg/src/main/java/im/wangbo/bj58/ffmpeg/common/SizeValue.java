package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class SizeValue implements Value {
    public final String value() {
        return sizeInBytes() + "B";
    }

    abstract long sizeInBytes();

    // TODO
    static SizeValue b(long sizeInBytes) {
        return new AutoValue_SizeValue(sizeInBytes);
    }

    // TODO
    static SizeValue kb(long sizeInBytes) {
        return new AutoValue_SizeValue(sizeInBytes * 1000L);
    }

    // TODO
    static SizeValue mb(long sizeInBytes) {
        return new AutoValue_SizeValue(sizeInBytes * 1000L * 1000L);
    }

    // TODO
    static SizeValue gb(long sizeInBytes) {
        return new AutoValue_SizeValue(sizeInBytes * 1000L * 1000L * 1000L);
    }
}
