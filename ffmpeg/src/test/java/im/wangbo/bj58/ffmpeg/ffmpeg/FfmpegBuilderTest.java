package im.wangbo.bj58.ffmpeg.ffmpeg;

import org.junit.jupiter.api.Test;

import im.wangbo.bj58.ffmpeg.executor.NativeExecutable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class FfmpegBuilderTest {
    @Test
    void build() {
        final String ffmpegPath = "jkasdhfkhasdfjhaksdhfkjasdhfjk";
        final String inputPath = "input input input";
        final String outputPath = "output output output";

        final FfmpegBuilder builder = FfmpegBuilder.builder(ffmpegPath);

        builder.addArg(Args.logLevel(Args.LogLevel.FATAL))
                .addInput(InputSource.builder(inputPath).build())
                .addOutput(OutputSink.builder(outputPath).build());
        
        NativeExecutable build = builder.build();
        System.out.println(build);
    }
}