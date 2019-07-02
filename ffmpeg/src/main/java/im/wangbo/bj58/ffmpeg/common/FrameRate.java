package im.wangbo.bj58.ffmpeg.common;

import java.util.OptionalInt;

/**
 * Frame rate in Hz
 *
 * @author Elvis Wang
 */
public interface FrameRate extends Value {
    @Override
    String asString();

    static FrameRate of(int frames, int seconds) {
        return FractionalBasedFrameRate.create(frames, OptionalInt.of(seconds));
    }

    static FrameRate of(int frames) {
        return FractionalBasedFrameRate.create(frames, OptionalInt.empty());
    }

    static FrameRate of(double fps) {
        return FloatingBasedFrameRate.of(fps);
    }

    static FrameRate of(float fps) {
        return FloatingBasedFrameRate.of(fps);
    }

    /**
     * TODO add brief description here
     *
     * @author Elvis Wang
     */
    enum Predefined implements FrameRate {
        NTSC(30000, 1001), // ntsc 30000/1001
        PAL(25, 1), // pal 25/1
        QNTSC(30000, 1001), // qntsc 30000/1001
        QPAL(25, 1), // qpal 25/1
        SNTSC(30000, 1001), // sntsc 30000/1001
        SPAL(25, 1), // spal 25/1
        FILM(24, 1), // film 24/1
        NTSC_FILM(24000, 1001), // ntsc_film 24000/1001
        ;

        private final int amount;
        private final int base;

        Predefined(final int amount, final int base) {
            this.amount = amount;
            this.base = base;
        }

        @Override
        public final String asString() {
            return amount + "/" + base;
        }
    }
}
