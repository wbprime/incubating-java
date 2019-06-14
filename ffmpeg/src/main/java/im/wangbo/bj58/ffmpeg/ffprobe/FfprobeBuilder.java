package im.wangbo.bj58.ffmpeg.ffprobe;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.common.HideBannerArg;
import im.wangbo.bj58.ffmpeg.arg.common.LogLevelArg;
import im.wangbo.bj58.ffmpeg.executor.NativeExecutable;
import im.wangbo.bj58.ffmpeg.executor.StdExecutor;
import im.wangbo.bj58.ffmpeg.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.ffprobe.writer.WriterDescription;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfprobeBuilder {
    private final String pathToExe;

    private final List<Arg> args = Lists.newArrayList();

    @Nullable
    private File pwDir;

    private File input;

    private WriterDescription writerDesc;

    private FfprobeBuilder(final String path) {
        this.pathToExe = path;
    }

    public static FfprobeBuilder builder(final String path) {
        return new FfprobeBuilder(path);
    }

    public FfprobeBuilder addArg(final Arg arg) {
        args.add(arg);
        return this;
    }

    public FfprobeBuilder hideBanner() {
        return addArg(HideBannerArg.of());
    }

    public FfprobeBuilder logLevel(final LogLevelArg arg) {
        return addArg(arg);
    }

    public FfprobeBuilder addSectionSpecifier(final SectionSpecifier specifier) {
        return addArg(SectionSpecifierArg.of(specifier));
    }

    public FfprobeBuilder addWriter(final WriterDescription writer) {
//        return addArg(SectionSpecifierArg.of(specifier));
        return this;
    }

    public FfprobeBuilder addInput(final File input) {
        this.input = input;
        return this;
    }

    public NativeExecutable build() {
        final List<String> strArgs = args.stream()
                .flatMap(arg -> arg.argValue().map(v -> Stream.of(arg.argName(), v)).orElse(Stream.of(arg.argName())))
                .collect(Collectors.toList());
        return NativeExecutable.builder()
                .workingDir(pwDir)
                .command(pathToExe)
                .addOpts(strArgs)
                .build();
    }

    public CompletionStage<MediaMetaInfo> buildAndExecute(final StdExecutor executor) {
        final NativeExecutable executable = build();
        return executor.execute(executable)
                .thenApply(p -> String.join("", p.stdoutLines()))
                .thenApply(str -> writerDesc.meta().parser().parse(str));
    }
}
