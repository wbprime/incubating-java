package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public enum SectionSpecifier {
    FORMAT("format"),
    STREAMS("streams"),
    FRAMES("frames"),
    PACKETS("packets"),
    CHAPTERS("chapters"),
    PROGRAMS("programs"),
    PIXEL_FORMATS("pixel_formats"),
    ERROR("error"),
    PROGRAM_VERSION("program_version"),
    LIBRARY_VERSIONS("library_versions"),
    ;

    private final String sectionName;

    SectionSpecifier(final String name) {
        this.sectionName = name;
    }

    public String sectionName() {
        return sectionName;
    }
}
