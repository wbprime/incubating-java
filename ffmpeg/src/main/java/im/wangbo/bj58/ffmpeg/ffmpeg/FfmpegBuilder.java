package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.executor.NativeExecutable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class FfmpegBuilder {
    private final String pathToExe;

    private final List<Arg> args = Lists.newArrayList();
    private File pwDir;

    private FfmpegBuilder(final String path) {
        this.pathToExe = path;
    }

    static FfmpegBuilder builder(final String path) {
        return new FfmpegBuilder(path);
    }

    public FfmpegBuilder addArg(final Arg arg) {
        args.add(arg);
        return this;
    }

    public NativeExecutable build() {
        final List<String> strArgs = args.stream()
                .flatMap(arg -> arg.argValue().map(v -> Stream.of(arg.argName(), v)).orElse(Stream.of(arg.argName())))
                .collect(Collectors.toList());
        return NativeExecutable.builder().command(pathToExe)
                .opts(strArgs)
                .workingDir(pwDir)
                .build();
    }

}
