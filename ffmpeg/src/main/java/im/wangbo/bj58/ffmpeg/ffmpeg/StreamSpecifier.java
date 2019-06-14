package im.wangbo.bj58.ffmpeg.ffmpeg;

import java.util.OptionalInt;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface StreamSpecifier extends Value {
    static StreamSpecifier all() {
        return AllStreamSpecifier.of();
    }

    static StreamSpecifier of(int index) {
        return IndexBasedStreamSpecifier.of(index);
    }

    static StreamSpecifier of(final MediaStreamType type) {
        return TypeBasedStreamSpecifier.of(type, OptionalInt.empty());
    }

    static StreamSpecifier of(final MediaStreamType type, final int index) {
        return TypeBasedStreamSpecifier.of(type, OptionalInt.of(index));
    }
}
