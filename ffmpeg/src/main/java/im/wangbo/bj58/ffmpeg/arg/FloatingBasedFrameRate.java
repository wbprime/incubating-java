package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

/**
 * Frame rate in Hz
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class FloatingBasedFrameRate implements FrameRate {
    @Override
    public abstract String asString();

    private static FloatingBasedFrameRate create(String asString) {
        return new AutoValue_FloatingBasedFrameRate(asString);
    }

    static FloatingBasedFrameRate of(double val) {
        return create(String.valueOf(val));
    }

    static FloatingBasedFrameRate of(float val) {
        return create(String.valueOf(val));
    }
}
