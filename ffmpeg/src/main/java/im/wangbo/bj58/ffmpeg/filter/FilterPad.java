package im.wangbo.bj58.ffmpeg.filter;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FilterPad {
    static FilterPad named(final String str) {
        return NamedFilterPad.of(str);
    }

    static FilterPad unnamed() {
        return UnnamedFilterPad.of();
    }
}
