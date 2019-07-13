package im.wangbo.bj58.ffmpeg.streamspecifier;

import java.util.OptionalInt;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface StreamSpecifier {
    String asString();

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
