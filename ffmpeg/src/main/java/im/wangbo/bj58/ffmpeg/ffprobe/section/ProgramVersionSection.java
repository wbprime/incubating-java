package im.wangbo.bj58.ffmpeg.ffprobe.section;

import java.util.StringJoiner;

/**
 * Sample data:
 *
 * "program_version": {
 *    "version": "n4.1.3",
 *    "copyright": "Copyright (c) 2007-2019 the FFmpeg developers",
 *    "compiler_ident": "gcc 8.2.1 (GCC) 20181127",
 *    "configuration": "--prefix=/usr --disable-debug --disable-static --disable-stripping --enable-fontconfig --enable-gmp --enable-gnutls
 *  --enable-gpl --enable-ladspa --enable-libaom --enable-libass --enable-libbluray --enable-libdrm --enable-libfreetype --e nable-libfribidi --enable-libgsm --enable-libiec61883 --enable-libjack --enable-libmodplug --enable-libmp3lame --enable-libopencore_amrn b --enable-libopencore_amrwb --enable-libopenjpeg --enable-libopus --enable-libpulse --enable-libsoxr --enable-libspeex --enable-libssh --enable-libtheora --enable-libv4l2 --enable-libvidstab --enable-libvorbis --enable-libvpx --enable-libwebp --enable-libx264 --enable-libx265 --enable-libxcb --enable-libxml2 --enable-libxvid --enable-nvdec --enable-nvenc --enable-omx --enable-shared --enable-version3"
 *  }
 *
 * @author Elvis Wang
 */
public class ProgramVersionSection {
    @Override
    public String toString() {
        return new StringJoiner(", ", ProgramVersionSection.class.getSimpleName() + "[", "]")
                .toString();
    }
}
