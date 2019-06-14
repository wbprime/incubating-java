package im.wangbo.bj58.ffmpeg.ffprobe.section;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public enum SectionSpecifier implements Value {
    CHAPTERS("-show_chapters"),
    FORMAT("show_format"),
    FRAMES("-show_frames"),
    PROGRAMS("-show_programs"),
    STREAMS("-show_streams"),
    PACKETS("-show_packets"),
    ERROR("-show_error"),
    PROGRAM_VERSION("-show_program_version"),
    LIBRARY_VERSIONS("-show_library_versions"),
    PIXEL_FORMATS("-show_pixel_formats"),
    ;

    private final String sectionName;

    SectionSpecifier(final String name) {
        this.sectionName = name;
    }

    @Override
    public final String asString() {
        return null;
    }
}
