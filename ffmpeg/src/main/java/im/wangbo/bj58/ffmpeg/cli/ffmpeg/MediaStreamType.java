package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public enum MediaStreamType {
    VIDEO('v'),
    AUDIO('a'),
    SUBTITLE('s'),
    DATA('d'),
    ATTACHMENT('t'),
    ;

    private final char specifierChar;

    MediaStreamType(char c) {
        this.specifierChar = c;
    }

    public final String specifier() {
        return String.valueOf(specifierChar);
    }
}
