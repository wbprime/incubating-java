package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Value {
    String asString();

    default Optional<String> stringify() {
        return Optional.of(asString());
    }

    static Value of() {
        return EmptyValue.of();
    }

    static Value of(final String value) {
        return StdValue.of(value);
    }

    static Value ofString(final String str) {
        return of(str);
    }

    static Value ofLong(final long l) {
        return of(String.valueOf(l));
    }

    static Value ofInt(final int l) {
        return of(String.valueOf(l));
    }
}
