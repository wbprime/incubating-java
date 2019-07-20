package im.wangbo.bj58.ffmpeg.cli.ffprobe.arg;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SectionSpecifierArg implements FfprobeArg {
    public abstract SectionSpecifier sectionSpecifier();

    @Override
    public String name() {
        switch (sectionSpecifier()) {
            case FORMAT:
                return "-show_format";
            case STREAMS:
                return "-show_streams";
            case FRAMES:
                return "-show_frames";
            case PACKETS:
                return "-show_packets";
            case CHAPTERS:
                return "-show_chapters";
            case PROGRAMS:
                return "-show_programs";
            case PIXEL_FORMATS:
                return "-show_pixel_formats";
            case ERROR:
                return "-show_error";
            case PROGRAM_VERSION:
                return "-show_program_version";
            case LIBRARY_VERSIONS:
                return "-show_library_versions";
            default:
                throw new IllegalStateException("Unexpected error");
        }
    }

    @Override
    public String description() {
        return "Show section for " + sectionSpecifier().sectionName();
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static SectionSpecifierArg of(final SectionSpecifier specifier) {
        return new AutoValue_SectionSpecifierArg(specifier);
    }
}
