package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.common.HideBannerArg;
import im.wangbo.bj58.ffmpeg.arg.common.LogLevelArg;
import im.wangbo.bj58.ffmpeg.arg.main.ShowProgressStatsArg;
import im.wangbo.bj58.ffmpeg.cli.executor.NativeExecutable;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.ComplexFilterGraph;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfmpegBuilder {

    private final String pathToExe;

    private final List<Arg> args = Lists.newArrayList();

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

    public FfmpegBuilder hideBanner() {
        return addArg(HideBannerArg.of());
    }

    public FfmpegBuilder showProgressStats(final boolean on) {
        return addArg(on ? ShowProgressStatsArg.on() : ShowProgressStatsArg.off());
    }

    public FfmpegBuilder logLevel(final LogLevelArg arg) {
        return addArg(arg);
    }

    public FfmpegBuilder addArg(final Arg arg) {
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

    public NativeExecutable build() {
        inputs.forEach(i -> args.addAll(i.asArgs()));
        outputs.forEach(o -> args.addAll(o.asArgs()));
        final List<String> strArgs = args.stream()
            .flatMap(arg -> arg.value().map(v -> Stream.of(arg.spec().name(), v.asString()))
                .orElse(Stream.of(arg.spec().name())))
            .collect(Collectors.toList());
        return NativeExecutable.builder()
            .workingDir(pwDir)
            .command(pathToExe)
            .addOpts(strArgs)
            .build();
    }
}
