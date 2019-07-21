package it;

import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.exec.CliRunningProcess;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.FfprobeBuilder;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.section.SectionSpecifier;
import im.wangbo.bj58.ffmpeg.cli.ffprobe.writer.WriterFormat;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
class FfprobeIT {
    private Path video;

    private ScheduledExecutorService threadPool;

    @BeforeEach
    void setUp() throws Exception {
        video = Paths.get("tmp." + UUID.randomUUID().toString() + ".mp4");
        try (InputStream in = getClass().getResourceAsStream("/big_buck_bunny.640x360.mp4")) {
            Files.copy(in, video);
        }

        threadPool = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.delete(video);

        threadPool.shutdown();
    }

    @Test
    void probe_build() throws Exception {
        final CliCommand command = FfprobeBuilder.builder("ffprobe")
            .hideBanner()
            .logLevel(LogLevelArg.LogLevel.WARNING)
            .showSection(SectionSpecifier.FORMAT)
            .showSection(SectionSpecifier.STREAMS)
            .writerFormat(WriterFormat.json())
            .build(video.toUri());

        final CliRunningProcess started = command.start(threadPool)
            .toCompletableFuture().get();

        try {
            Assertions.assertThat(started.waitFor() == 0);
        } finally {
            started.close();
        }
    }

    @Test
    void probe_buildAndExecute() throws Exception {
        FfprobeBuilder.builder("ffprobe")
            .hideBanner()
            .logLevel(LogLevelArg.LogLevel.WARNING)
            .showSection(SectionSpecifier.FORMAT)
            .showSection(SectionSpecifier.STREAMS)
            .buildExecuted(video.toUri(), threadPool)
            .whenComplete(
                (result, ex) -> {
                    if (result != null) {
                        System.out.println(result);
                    } else {
                        System.err.println(ex);
                    }
                }
            ).toCompletableFuture().get();
    }
}
