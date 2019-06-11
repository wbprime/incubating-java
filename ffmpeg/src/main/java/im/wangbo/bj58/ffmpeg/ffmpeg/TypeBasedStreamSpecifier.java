package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.auto.value.AutoValue;

import java.util.OptionalInt;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class TypeBasedStreamSpecifier implements StreamSpecifier {
    @Override
    public final String asString() {
        return additionalIndex().isPresent() ?
                streamType().specifier() + ":" + additionalIndex().getAsInt() :
                streamType().specifier();
    }

    abstract MediaStreamType streamType();

    abstract OptionalInt additionalIndex();

    static TypeBasedStreamSpecifier of(MediaStreamType streamType, OptionalInt additionalIndex) {
        return new AutoValue_TypeBasedStreamSpecifier(streamType, additionalIndex);
    }
}
