package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;

import java.util.OptionalInt;

/**
 * Frame rate in Hz
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class FractionalBasedFrameRate implements FrameRate {
    @Override
    public final String asString() {
        return base().isPresent() ? amount() + "/" + base().getAsInt() : String.valueOf(amount());
    }

    abstract int amount();

    abstract OptionalInt base();

    static FractionalBasedFrameRate create(int amount, OptionalInt base) {
        return new AutoValue_FractionalBasedFrameRate(amount, base);
    }
}
