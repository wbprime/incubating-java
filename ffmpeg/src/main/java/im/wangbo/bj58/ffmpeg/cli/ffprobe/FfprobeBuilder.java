package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.exec.CliPidGeneratingStrategy;
import im.wangbo.bj58.ffmpeg.cli.exec.StdoutCollector;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.FfArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.InputUriArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.SectionSpecifierArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.arg.WriterFormatArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;

import static im.wangbo.bj58.ffmpeg.cli.exec.CliProcessTimeoutingStrategy.unlimited;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfprobeBuilder {
    private final MutableList<FfArg> ffArgs = Lists.mutable.empty();

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

    public FfprobeBuilder logLevel(final LogLevelArg.LogLevel logLevel) {
        return addArg(LogLevelArg.of(logLevel));
    }

    public FfprobeBuilder showSection(final SectionSpecifier specifier) {
        return addArg(SectionSpecifierArg.of(specifier));
    }

    public FfprobeBuilder writerFormat(final WriterFormat writer) {
        this.writerFormat = writer;
        return this;
    }

    public FfprobeBuilder workingDirectory(final File pwd) {
        if (!pwd.isDirectory())
            throw new IllegalArgumentException("pwd should be a valid directory path but was not: " + pwd);
        this.pwDir = pwd;
        return this;
    }

    private CliCommand build(final InputUriArg inputUriArg) {
        ffArgs.add(WriterFormatArg.of(writerFormat));
        ffArgs.add(inputUriArg);
        return CliCommand.builder()
            .command(pathToExe)
            .addArgs(ffArgs)
            .workingDirectory(pwDir)
            .build();
    }

    public CliCommand build(final URI uri) {
        return build(InputUriArg.of(uri));
    }

    public CliCommand build(final Path path) {
        return build(InputUriArg.of(path));
    }

    private CompletionStage<MediaMetaInfo> buildExecuted(
        final CliCommand cli,
        final ScheduledExecutorService executor) {

        final StdoutCollector stdout = StdoutCollector.of();
        return cli.start(executor, CliPidGeneratingStrategy.seqBased("ffprobe_"))
            .thenCompose(process -> process.awaitTerminated(executor, unlimited(), stdout, 0))
            .thenApply(process -> writerFormat.meta().parser().parse(stdout.collect()));
    }

    public CompletionStage<MediaMetaInfo> buildExecuted(
        final URI uri,
        final ScheduledExecutorService executor) {
        return buildExecuted(build(uri), executor);
    }

    public CompletionStage<MediaMetaInfo> buildExecuted(
        final Path path,
        final ScheduledExecutorService executor) {
        return buildExecuted(build(path), executor);
    }
}
