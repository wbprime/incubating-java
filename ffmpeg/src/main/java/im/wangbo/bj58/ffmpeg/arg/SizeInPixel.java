package im.wangbo.bj58.ffmpeg.arg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SizeInPixel implements Value {
    @Override
    public final String asString() {
        return width() + "x" + height();
    }

    public abstract int width();

    public abstract int height();

    public static SizeInPixel of(int w, int h) {
        return new AutoValue_SizeInPixel(w, h);
    }

    /*
     * See http://ffmpeg.org/ffmpeg-utils.html#Video-size
     */
    public static enum Predefined {
        size_ntsc(720, 480),
        size_pal(720, 576),
        size_qntsc(352, 240),
        size_qpal(352, 288),
        size_sntsc(640, 480),
        size_spal(768, 576),
        size_film(352, 240),
        size_ntsc_film(352, 240),
        size_sqcif(128, 96),
        size_qcif(176, 144),
        size_cif(352, 288),
        size_4cif(704, 576),
        size_16cif(1408, 1152),
        size_qqvga(160, 120),
        size_qvga(320, 240),
        size_vga(640, 480),
        size_svga(800, 600),
        size_xga(1024, 768),
        size_uxga(1600, 1200),
        size_qxga(2048, 1536),
        size_sxga(1280, 1024),
        size_qsxga(2560, 2048),
        size_hsxga(5120, 4096),
        size_wvga(852, 480),
        size_wxga(1366, 768),
        size_wsxga(1600, 1024),
        size_wuxga(1920, 1200),
        size_woxga(2560, 1600),
        size_wqsxga(3200, 2048),
        size_wquxga(3840, 2400),
        size_whsxga(6400, 4096),
        size_whuxga(7680, 4800),
        size_cga(320, 200),
        size_ega(640, 350),
        size_hd480(852, 480),
        size_hd720(1280, 720),
        size_hd1080(1920, 1080),
        size_2k(2048, 1080),
        size_2kflat(1998, 1080),
        size_2kscope(2048, 858),
        size_4k(4096, 2160),
        size_4kflat(3996, 2160),
        size_4kscope(4096, 1716),
        size_nhd(640, 360),
        size_hqvga(240, 160),
        size_wqvga(400, 240),
        size_fwqvga(432, 240),
        size_hvga(480, 320),
        size_qhd(960, 540),
        size_2kdci(2048, 1080),
        size_4kdci(4096, 2160),
        size_uhd2160(3840, 2160),
        size_uhd4320(7680, 4320),
        ;

        private final int w;
        private final int h;

        Predefined(int w, int h) {
            this.w = w;
            this.h = h;
        }

        public SizeInPixel size() {
            return SizeInPixel.of(w, h);
        }
    }
}
