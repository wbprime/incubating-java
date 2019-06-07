package im.wangbo.bj58.ffmpeg.executor;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class ConstantFilenameStrategy implements FilenameGeneratingStrategy {
    private final String filename;

    ConstantFilenameStrategy(final String str) {
        this.filename = str;
    }

    @Override
    public String apply(final String pid) {
        return filename;
    }
}
