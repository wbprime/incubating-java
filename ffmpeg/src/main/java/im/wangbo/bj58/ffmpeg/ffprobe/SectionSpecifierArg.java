package im.wangbo.bj58.ffmpeg.ffprobe;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.ffprobe.section.SectionSpecifier;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SectionSpecifierArg implements FfprobeArg {
    abstract SectionSpecifier sectionSpecifier();

    @Override
    public final String argName() {
        return sectionSpecifier().asString();
    }

    @Override
    public final String description() {
        return "";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static SectionSpecifierArg of(SectionSpecifier specifier) {
        return new AutoValue_SectionSpecifierArg(specifier);
    }
}
