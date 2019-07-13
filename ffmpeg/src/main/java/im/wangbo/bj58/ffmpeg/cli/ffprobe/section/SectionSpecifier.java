package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public enum SectionSpecifier {
    FORMAT("-show_format"),
    STREAMS("-show_streams"),
    FRAMES("-show_frames"),
    PACKETS("-show_packets"),
    CHAPTERS("-show_chapters"),
    PROGRAMS("-show_programs"),
    PIXEL_FORMATS("-show_pixel_formats"),
    ERROR("-show_error"),
    PROGRAM_VERSION("-show_program_version"),
    LIBRARY_VERSIONS("-show_library_versions"),
    ;

    private final String sectionName;

    SectionSpecifier(final String name) {
        this.sectionName = name;
    }

    public String sectionName() {
        return sectionName;
    }
}
