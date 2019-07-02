package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.arg.FfprobeArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.common.Value;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SectionSpecifierArg implements FfprobeArg {
    abstract SectionSpecifier sectionSpecifier();

    @Override
    public final ArgSpec spec() {
        return ArgSpec.of(
            sectionSpecifier().asString(),
            sectionSpecifier().asString()
        );
    }

    @Override
    public final Optional<Value> value() {
        return Optional.empty();
    }

    public static SectionSpecifierArg of(final SectionSpecifier specifier) {
        return new AutoValue_SectionSpecifierArg(specifier);
    }
}
