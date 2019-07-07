package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.io.ByteStreams;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Disabled("OS platform dependent")
class RunningProcessTest {
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

        final RunningProcess process = executable.start(scheduledPool)
            .toCompletableFuture().get();

        Assertions.assertThat(process.waitFor()).isEqualTo(0);

        try (
            final InputStream in = process.stderr();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            ByteStreams.copy(in, out);

            Assertions.assertThat(out.toByteArray()).isEmpty();
        }

        process.close();
    }

    @Test
    void runCommand_failed_emptyStderr() throws Exception {
        final CliCommand executable = CliCommand.of("false");

        final RunningProcess process = executable.start(scheduledPool)
            .toCompletableFuture().get();

        Assertions.assertThat(process.waitFor()).isEqualTo(1);

        try (
            final InputStream in = process.stderr();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            ByteStreams.copy(in, out);

            Assertions.assertThat(out.toByteArray()).isEmpty();
        }

        process.close();
    }

    @Test
    void runCommand_succeed_linesStdout() throws Exception {
        final CliCommand executable = CliCommand.builder()
            .command("echo")
            .addArg("jfjasfkjasf")
            .addArg("hajkdfhsadhf")
            .build();

        final RunningProcess process = executable.start(scheduledPool)
            .toCompletableFuture().get();

        Assertions.assertThat(process.waitFor()).isEqualTo(0);

        try (
            final InputStream in = process.stdout();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            ByteStreams.copy(in, out);

            final byte[] bytes = out.toByteArray();
            Assertions.assertThat(bytes).isNotEmpty();
            Assertions.assertThat(new String(bytes)).isEqualTo("jfjasfkjasf hajkdfhsadhf" + System.lineSeparator());
        }

        process.close();
    }

    @Test
    void runCommand_failed_linesStderr() throws Exception {
        final CliCommand executable = CliCommand.of("cat", "a");

        final RunningProcess process = executable.start(scheduledPool)
            .toCompletableFuture().get();

        Assertions.assertThat(process.waitFor()).isEqualTo(1);

        try (
            final InputStream in = process.stderr();
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ) {
            ByteStreams.copy(in, out);

            final byte[] bytes = out.toByteArray();
            Assertions.assertThat(bytes).isNotEmpty();
            Assertions.assertThat(new String(bytes)).isEqualTo("cat: a: No such file or directory" + System.lineSeparator());
        }

        process.close();
    }

    @Test
    void runCommand_illegalCommand_noArgs() throws Exception {
        final CliCommand executable = CliCommand.of("wo_ting_bu_jian");

        final CompletableFuture<RunningProcess> future = executable.start(scheduledPool)
            .toCompletableFuture();

        Assertions.assertThatThrownBy(future::get)
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(CliStartingException.class)
            .hasMessageContaining("Failed to start cli command \"wo_ting_bu_jian\"")
            .hasMessageContaining("with no args")
            .hasMessageContaining("with no environments")
            .hasMessageContaining("with working directory:");
    }

    @Test
    void runCommand_illegalCommand_withArgs() throws Exception {
        final CliCommand executable = CliCommand.of("ni_shuo_shen_me", "a");

        final CompletableFuture<RunningProcess> future = executable.start(scheduledPool)
            .toCompletableFuture();

        Assertions.assertThatThrownBy(future::get)
            .isInstanceOf(ExecutionException.class)
            .hasCauseInstanceOf(CliStartingException.class)
            .hasMessageContaining("Failed to start cli command \"ni_shuo_shen_me\"")
            .hasMessageContaining("with args:")
            .hasMessageContaining("\"a\"")
            .hasMessageContaining("with no environments")
            .hasMessageContaining("with working directory:");
    }
}
