package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.FfArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.FfmpegArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.ShowProgressStatsArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.ComplexFilterGraph;

import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfmpegBuilder {

    private final String pathToExe;

    private final List<FfArg> args = Lists.newArrayList();

    @Nullable
    private File pwDir;

    private final List<InputSource> inputs = Lists.newArrayList();
    private final List<OutputSink> outputs = Lists.newArrayList();

    private FfmpegBuilder(final String path) {
        this.pathToExe = path;
    }

    public static FfmpegBuilder builder(final String path) {
        return new FfmpegBuilder(path);
    }

//    public FfmpegBuilder hideBanner() {
//        return addArg(HideBannerArg.of());
//    }
//
    public FfmpegBuilder showProgressStats(final boolean on) {
        return addArg(on ? ShowProgressStatsArg.on() : ShowProgressStatsArg.off());
    }

//    public FfmpegBuilder logLevel(final LogLevelArg arg) {
//        return addArg(arg);
//    }

    public FfmpegBuilder addArg(final FfmpegArg arg) {
        args.add(arg);
        return this;
    }

    public FfmpegBuilder addInput(final InputSource.Builder builder) {
        return addInput(builder.build());
    }

    public FfmpegBuilder addInput(final InputSource input) {
        inputs.add(input);
        return this;
    }

    public FfmpegBuilder addOutput(final OutputSink.Builder builder) {
        return addOutput(builder.build());
    }

    public FfmpegBuilder addOutput(final OutputSink output) {
        outputs.add(output);
        return this;
    }

    public FfmpegBuilder addFilterGraph(final ComplexFilterGraph filterGraph) {
//        args.add(Arg.paired("-filter_complex", filterGraph));
        return this;
    }

//    public FfmpegBuilder addFilterChain(final FilterChain filterChain) {
//        return this;
//    }

    public CliCommand build() {
        inputs.forEach(i -> args.addAll(i.asArgs()));
        outputs.forEach(o -> args.addAll(o.asArgs()));
        return CliCommand.builder()
            .command(pathToExe)
            .addArgs(args)
            .workingDirectory(pwDir)
            .build();
    }
}
