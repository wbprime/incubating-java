package im.wangbo.bj58.ffmpeg.ffmpeg;

import im.wangbo.bj58.ffmpeg.arg.common.ShowLicenseArg;
import im.wangbo.bj58.ffmpeg.arg.common.ShowVersionArg;
import im.wangbo.bj58.ffmpeg.executor.NativeExecutable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class Ffmpegs {
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
