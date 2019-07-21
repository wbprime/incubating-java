package it;

import im.wangbo.bj58.ffmpeg.cli.exec.CliCommand;
import im.wangbo.bj58.ffmpeg.cli.ff.arg.LogLevelArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.FfmpegBuilder;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.InputSource;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.OutputSink;
import im.wangbo.bj58.ffmpeg.common.FrameRate;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;
import im.wangbo.bj58.ffmpeg.streamspecifier.MediaStreamType;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
class FfmpegIT {
    private Path video;
    private Path tmpDir;

    private ScheduledExecutorService threadPool;

    @BeforeEach
    void setUp() throws Exception {
        video = Paths.get("tmp." + UUID.randomUUID().toString() + ".mp4");
        try (InputStream in = getClass().getResourceAsStream("/big_buck_bunny.640x360.mp4")) {
            Files.copy(in, video);
        }

        tmpDir = Paths.get("tmp." + UUID.randomUUID().toString() + ".dir");
        Files.createDirectory(tmpDir);

        threadPool = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.delete(video);
        Files.list(tmpDir).forEach(p -> {
            try {
                Files.delete(p);
            } catch (IOException e) {
                // Do nothing
            }
        });
        Files.delete(tmpDir);

        threadPool.shutdown();
    }

    @Test
    void snapshotToPng() throws Exception {
        final CliCommand command = FfmpegBuilder.builder("ffmpeg")
            .hideBanner()
            .logLevel(LogLevelArg.LogLevel.WARNING)
            .addInput(InputSource.builder(video).build())
            .addOutput(OutputSink.builder(tmpDir.resolve("tmp.%05d.png"))
                .limitOutputFrames(StreamSpecifier.of(MediaStreamType.VIDEO), 10)
                .frameRate(FrameRate.of(2))
                .mediaFormat(MediaFormat.image2())
                .build())
            .build();

//        System.out.println(runCommand(command));
//        System.out.println(command);
    }

    private Duration runCommand(final CliCommand command) throws Exception {
        return command.start(threadPool)
            .thenCompose(process -> process.awaitTerminated(threadPool, 0))
            .thenApply(process -> Duration.between(process.startedTime(), process.terminatedTime()))
            .toCompletableFuture().get();
    }
}
