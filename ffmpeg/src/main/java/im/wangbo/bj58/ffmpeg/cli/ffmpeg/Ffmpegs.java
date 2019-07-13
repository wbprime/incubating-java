package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

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

//    public static CliCommand showLicense(final String ffmpeg) {
//        return FfmpegBuilder.builder(ffmpeg)
//            .addArg(ShowLicenseArg.of())
//            .build();
//    }

//    public static CliCommand showVersion(final String ffmpeg) {
//        return FfmpegBuilder.builder(ffmpeg)
//            .addArg(ShowVersionArg.of())
//            .build();
//    }
}
