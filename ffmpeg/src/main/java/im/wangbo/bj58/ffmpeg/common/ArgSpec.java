package im.wangbo.bj58.ffmpeg.common;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface ArgSpec {
    String name();

    String description();

    public static ArgSpec of(final String name, final String description) {
        return null;
    }
}
