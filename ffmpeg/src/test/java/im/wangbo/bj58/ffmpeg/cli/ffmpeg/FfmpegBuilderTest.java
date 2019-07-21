package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.HideBannerArg;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

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
            .addInput(InputSource.builder(URI.create(inputPath)).build())
            .addOutput(OutputSink.builder(URI.create(outputPath)).build());

        CliCommand build = builder.build();

        System.out.println(build);
        Assertions.assertThat(build).isNotNull();
        Assertions.assertThat(build.commandLines()).isNotEmpty()
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
