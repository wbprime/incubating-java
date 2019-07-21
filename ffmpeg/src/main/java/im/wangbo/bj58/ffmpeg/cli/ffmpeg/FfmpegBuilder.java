package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.exec.CliPidGeneratingStrategy;
import im.wangbo.bj58.ffmpeg.cli.exec.CliProcessTimeoutingStrategy;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.FfArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.ComplexFilterArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.ShowProgressStatsArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterGraph;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;

import javax.annotation.Nullable;
import java.io.File;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfmpegBuilder {

    private final String pathToExe;

    private final MutableList<FfArg> args = Lists.mutable.empty();

    @Nullable
    private File pwDir;

    private final MutableList<InputSource> inputs = Lists.mutable.empty();
    private final MutableList<OutputSink> outputs = Lists.mutable.empty();

    private FfmpegBuilder(final String path) {
        this.pathToExe = path;
    }

    public static FfmpegBuilder builder(final String path) {
        return new FfmpegBuilder(path);
    }

    /**
     * "-hide_banner" option
     *
     * @return this
     */
    public FfmpegBuilder hideBanner() {
        return addArg(HideBannerArg.of());
    }

    /**
     * "-stats" or "-nostats" option
     *
     * @return this
     */
    public FfmpegBuilder showProgressStats(final boolean on) {
        return addArg(on ? ShowProgressStatsArg.on() : ShowProgressStatsArg.off());
    }

    /**
     * "-loglevel" option
     *
     * @return this
     */
    public FfmpegBuilder logLevel(final LogLevelArg.LogLevel logLevel) {
        return addArg(LogLevelArg.of(logLevel));
    }

    public FfmpegBuilder addArg(final FfArg arg) {
        args.add(arg);
        return this;
    }

    public FfmpegBuilder addInput(final InputSource input) {
        inputs.add(input);
        return this;
    }

    public FfmpegBuilder addOutput(final OutputSink output) {
        outputs.add(output);
        return this;
    }

    /**
     * "-filter_complex" option
     *
     * @return this
     */
    public FfmpegBuilder addFilterGraph(final FilterGraph filterGraph) {
        addArg(ComplexFilterArg.of(filterGraph));
        return this;
    }

    public FfmpegBuilder workingDirectory(final File pwd) {
        if (!pwd.isDirectory())
            throw new IllegalArgumentException("pwd should be a valid directory path but was not: " + pwd);
        this.pwDir = pwd;
        return this;
    }

    public CliCommand build() {
        inputs.forEach(i -> args.addAllIterable(i.asArgs()));
        outputs.forEach(o -> args.addAllIterable(o.asArgs()));
        return CliCommand.builder()
            .command(pathToExe)
            .addArgs(args)
            .workingDirectory(pwDir)
            .build();
    }

    public CompletionStage<Duration> buildExecuted(
        final ScheduledExecutorService executor,
        final CliProcessTimeoutingStrategy timeoutingStrategy) {

        final CliCommand cli = build();

        return cli.start(executor, CliPidGeneratingStrategy.seqBased("ffmpeg_"))
            .thenCompose(process -> process.awaitTerminated(executor, timeoutingStrategy, 0))
            .thenApply(process -> Duration.between(process.startedTime(), process.terminatedTime()));
    }

    public CompletionStage<Duration> buildExecuted(
        final ScheduledExecutorService executor, final Duration timeout) {

        return buildExecuted(executor, CliProcessTimeoutingStrategy.limited(timeout));
    }

    public CompletionStage<Duration> buildExecuted(
        final ScheduledExecutorService executor) {

        return buildExecuted(executor, CliProcessTimeoutingStrategy.unlimited());
    }
}
