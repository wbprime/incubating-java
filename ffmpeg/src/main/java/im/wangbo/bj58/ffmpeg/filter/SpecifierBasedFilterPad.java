package im.wangbo.bj58.ffmpeg.filter;

import com.google.auto.value.AutoValue;

import java.util.OptionalInt;

import im.wangbo.bj58.ffmpeg.ffmpeg.StreamSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class SpecifierBasedFilterPad implements FilterPad {
    /**
     * @return index of input files or output files
     */
    abstract OptionalInt uriIndex();

    abstract StreamSpecifier streamSpecifier();

    @Override
    public final String encode() {
        final String str = uriIndex().isPresent() ?
                uriIndex().getAsInt() + ":" + streamSpecifier().encode() :
                streamSpecifier().encode();
        return "[" + str + "]";
    }

    static SpecifierBasedFilterPad of(OptionalInt uriIndex, StreamSpecifier streamSpecifier) {
        return new AutoValue_SpecifierBasedFilterPad(uriIndex, streamSpecifier);
    }
}
