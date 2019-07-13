package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.exec.StdoutCollector;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.FfArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.InputUriArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.SectionSpecifierArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.WriterFormatArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfprobeBuilder {
    private final List<FfArg> ffArgs = Lists.newArrayList();

    private final String pathToExe;

    @Nullable
    private File pwDir;

    private WriterFormat writerFormat = WriterFormat.json();

    private FfprobeBuilder(final String path) {
        this.pathToExe = path;
    }

    public static FfprobeBuilder builder(final String path) {
        return new FfprobeBuilder(path);
    }

    private FfprobeBuilder addArg(final FfArg arg) {
        ffArgs.add(arg);
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

    public CliCommand build(final URI uri) {
        ffArgs.add(WriterFormatArg.of(writerFormat));
        ffArgs.add(InputUriArg.of(uri));
        return CliCommand.builder()
            .command(pathToExe)
//            .addArgs(ffArgs)
            .workingDirectory(pwDir)
            .build();
    }

    public CompletionStage<MediaMetaInfo> buildAndExecute(final URI uri,
                                                          final ScheduledExecutorService executor) {
        final CliCommand cli = build(uri);

        final StdoutCollector stdout = StdoutCollector.of();
        return cli.start(executor)
            .thenCompose(process -> process.awaitTerminated(executor, stdout, 0))
            .thenApply(process -> writerFormat.meta().parser().parse(stdout.collect()));
    }
}
