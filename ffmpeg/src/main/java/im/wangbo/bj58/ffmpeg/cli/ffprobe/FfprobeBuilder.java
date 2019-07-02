package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.cli.executor.NativeExecutable;
import im.wangbo.bj58.ffmpeg.cli.executor.StdExecutor;
import im.wangbo.bj58.ffmpeg.cli.common.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.common.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.FfprobeArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.InputUriArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.SectionSpecifierArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.WriterFormatArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfprobeBuilder {

    private final String pathToExe;

    private final List<FfprobeArg> args = Lists.newArrayList();

    @Nullable
    private File pwDir;

    private WriterFormat writerFormat = WriterFormat.json();

    private FfprobeBuilder(final String path) {
        this.pathToExe = path;
    }

    public static FfprobeBuilder builder(final String path) {
        return new FfprobeBuilder(path);
    }

    public FfprobeBuilder addArg(final FfprobeArg arg) {
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

    public FfprobeBuilder writerFormat(final WriterFormat writer) {
        this.writerFormat = writer;
        return this;
    }

    private Stream<String> stringifyArg(final FfprobeArg arg) {
        return arg.value().isPresent() ?
            Stream.of(arg.spec().name(), arg.value().get().asString()) :
            Stream.of(arg.spec().name());
    }

    public NativeExecutable build(final URI uri) {
        args.add(WriterFormatArg.of(writerFormat));
        args.add(InputUriArg.of(uri));
        final List<String> strArgs = args.stream()
            .flatMap(this::stringifyArg)
            .collect(Collectors.toList());
        return NativeExecutable.builder()
            .workingDir(pwDir)
            .command(pathToExe)
            .addOpts(strArgs)
            .stderrToFile(true)
            .stdoutToFile(true)
            .build();
    }

    public CompletionStage<MediaMetaInfo> buildAndExecute(final URI uri,
        final StdExecutor executor) {
        final NativeExecutable executable = build(uri);
        return executor.execute(executable)
            .thenApply(p -> {
                final String out = String.join("", p.stdoutLines());
                p.close();
                return out;
            })
            .thenApply(str -> writerFormat.meta().parser().parse(str));
    }
}
