package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.io.ByteStreams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-07, by Elvis Wang
 */
class TerminatedProcessTest {
    private ScheduledExecutorService scheduledPool;

    @BeforeEach
    void setUp() {
        scheduledPool = Executors.newScheduledThreadPool(4);
    }

    @AfterEach
    void tearDown() {
        scheduledPool.shutdown();
    }

    @Test
    void runCommand_succeed_emptyStderr() throws Exception {
        final CliCommand executable = CliCommand.of("true");

        final StdoutCollector stdout = StdoutCollector.of();
        executable.start(scheduledPool)
            .thenCompose(p -> p.awaitTerminated(scheduledPool, stdout, 0))
            .toCompletableFuture().get();

        Assertions.assertThat(stdout.collect()).isEqualTo("");
    }

    @Test
    void runCommand_succeed2_emptyStderr() throws Exception {
        final CliCommand executable = CliCommand.of("false");

        final StdoutCollector stdout = StdoutCollector.of();
        executable.start(scheduledPool)
            .thenCompose(p -> p.awaitTerminated(scheduledPool, stdout, 1))
            .toCompletableFuture().get();

        Assertions.assertThat(stdout.collect()).isEqualTo("");
    }

    @Test
    void runCommand_failed_emptyStderr() throws Exception {
        final CliCommand executable = CliCommand.of("false");

        final StdoutCollector stdout = StdoutCollector.of();
        final CompletableFuture<TerminatedProcess> future = executable.start(scheduledPool)
            .thenCompose(p -> p.awaitTerminated(scheduledPool, stdout, 0))
            .toCompletableFuture();

        Assertions.assertThatThrownBy(future::get)
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(CliRunningException.class)
            .hasMessageContaining("Failed to run cli command \"false\"")
            .hasMessageContaining("with not args")
            .hasMessageContaining("with no environments")
            .hasMessageContaining("with working directory:")
            .hasMessageContaining("with process id:")
            .hasMessageContaining("with started time:")
            .hasMessageContaining("with stderr lines:")
        ;
    }

    @Test
    void runCommand_succeed_linesStdout() throws Exception {
        final CliCommand executable = CliCommand.builder()
            .command("echo")
            .addArg("jfjasfkjasf")
            .addArg("hajkdfhsadhf")
            .build();

        final StdoutCollector stdout = StdoutCollector.of();
        executable.start(scheduledPool)
            .thenCompose(p -> p.awaitTerminated(scheduledPool, stdout, 0))
            .toCompletableFuture().get();

        Assertions.assertThat(stdout.collect()).isEqualTo("jfjasfkjasf hajkdfhsadhf");
    }

    @Test
    void runCommand_failed_linesStderr() throws Exception {
        final CliCommand executable = CliCommand.of("cat", "a", "b");

        final StdoutCollector stdout = StdoutCollector.of();
        final CompletableFuture<TerminatedProcess> future = executable.start(scheduledPool)
            .thenCompose(p -> p.awaitTerminated(scheduledPool, stdout, 0))
            .toCompletableFuture();

        Assertions.assertThatThrownBy(future::get)
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(CliRunningException.class)
            .hasMessageContaining("Failed to run cli command \"cat\"")
            .hasMessageContaining("with args:")
            .hasMessageContaining("\"a\"")
            .hasMessageContaining("with no environments")
            .hasMessageContaining("with working directory:")
            .hasMessageContaining("with process id:")
            .hasMessageContaining("with started time:")
            .hasMessageContaining("with stderr lines:")
            ;
    }
}
