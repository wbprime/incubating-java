package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import java.util.OptionalInt;

import im.wangbo.bj58.ffmpeg.arg.Value;
import im.wangbo.bj58.ffmpeg.ffmpeg.StreamSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FilterPad extends Value {
    static FilterPad named(final String str) {
        return NamedFilterPad.of(str);
    }

    static FilterPad unnamed() {
        return UnnamedFilterPad.of();
    }

    static FilterPad streamSpecifier(final int index, final StreamSpecifier streamSpecifier) {
        return SpecifierBasedFilterPad.of(OptionalInt.of(index), streamSpecifier);
    }

    static FilterPad streamSpecifier(final StreamSpecifier streamSpecifier) {
        return SpecifierBasedFilterPad.of(OptionalInt.empty(), streamSpecifier);
    }
}
