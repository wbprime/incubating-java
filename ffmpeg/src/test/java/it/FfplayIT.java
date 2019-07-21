package it;

import im.wangbo.bj58.ffmpeg.cli.exec.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
class FfplayIT {
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
    void play() throws Exception {
        final CliCommand command = CliCommand.of("ffplay", video.toString());

        final CompletableFuture<CliTerminatedProcess> future = command.start(threadPool)
            .thenCompose(p -> p.awaitTerminated(threadPool,
                CliProcessTimeoutingStrategy.limitedInSeconds(5), 0))
            .toCompletableFuture();

        Assertions.assertThatThrownBy( future::get )
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(CliInterruptedException.class);
    }
}
