package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import im.wangbo.bj58.ffmpeg.cli.common.arg.ShowLicenseArg;
import im.wangbo.bj58.ffmpeg.cli.common.arg.ShowVersionArg;
import im.wangbo.bj58.ffmpeg.cli.executor.NativeExecutable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class Ffmpegs {
    private Ffmpegs() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public final FfmpegBuilder builder(final String ffmpeg) {
        return FfmpegBuilder.builder(ffmpeg);
    }

    public static NativeExecutable showLicense(final String ffmpeg) {
        return FfmpegBuilder.builder(ffmpeg)
                .addArg(ShowLicenseArg.of())
                .build();
    }

    public static NativeExecutable showVersion(final String ffmpeg) {
        return FfmpegBuilder.builder(ffmpeg)
                .addArg(ShowVersionArg.of())
                .build();
    }
}