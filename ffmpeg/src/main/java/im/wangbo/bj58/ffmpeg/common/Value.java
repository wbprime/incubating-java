package im.wangbo.bj58.ffmpeg.common;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Value extends DescribeTo {
    String asString();

    @Override
    default void describeTo(final StringBuilder sb) {
        sb.append(asString());
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
