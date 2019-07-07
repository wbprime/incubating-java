package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.cli.common.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.common.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

        builder
                .addArg(HideBannerArg.of())
                .addArg(LogLevelArg.of(LogLevelArg.LogLevel.FATAL))
                .addInput(InputSource.builder(inputPath).build())
                .addOutput(OutputSink.builder(outputPath).build());

        CliCommand build = builder.build();

        System.out.println(build);
        Assertions.assertThat(build).isNotNull();
        Assertions.assertThat(build.commandAndArgs()).isNotEmpty()
                .containsExactlyElementsOf(
                        ImmutableList.<String>builder()
                                .add(ffmpegPath)
                                .add("-hide_banner")
                                .add("-loglevel").add("8")
                                // Input
                                .add("-i").add(inputPath)
                                // Output
                                .add(outputPath)
                                .build()
                );
    }
}
