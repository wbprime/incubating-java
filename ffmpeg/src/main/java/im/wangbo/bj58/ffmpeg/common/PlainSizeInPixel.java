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
    public abstract int w();

    @Override
    public abstract int h();

    static PlainSizeInPixel of(int w, int h) {
        return new AutoValue_PlainSizeInPixel(w, h);
    }
}
