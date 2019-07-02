package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class PlainSizeInPixel implements SizeInPixel {
    @Override
    public final String asString() {
        return width() + "x" + height();
    }

    abstract int width();

    abstract int height();

    static PlainSizeInPixel of(int w, int h) {
        return new AutoValue_PlainSizeInPixel(w, h);
    }
}
