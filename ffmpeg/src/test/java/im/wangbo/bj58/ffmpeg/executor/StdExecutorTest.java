package im.wangbo.bj58.ffmpeg.executor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Disabled("OS platform dependent")
class StdExecutorTest {
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
    void runEcho_redirect() throws Exception {
        final StdExecutor executor = StdExecutor.create(scheduledPool);

        final NativeExecutable executable = NativeExecutable.builder()
                .command("echo")
                .addOpt("jfjasfkjasf")
                .addOpt("hajkdfhsadhf")
                .stderrToFile(true)
                .stderrToFile(true)
                .build();

        final NativeProcess process = executor.execute(executable).get();
        process.close();
    }

    @Test
    void runEcho() throws Exception {
        final StdExecutor executor = StdExecutor.create(scheduledPool);

        final NativeExecutable executable = NativeExecutable.of("echo", "1", "2");

        final NativeProcess process = executor.execute(executable).get();
        process.close();
    }

    @Test
    void runCommand_false_failed() throws Exception {
        final StdExecutor executor = StdExecutor.create(scheduledPool);

        final NativeExecutable executable = NativeExecutable.of("false");

        final NativeProcess process = executor.execute(executable).get();
        process.close();
    }

    @Test
    void runCommand_grep_failed() throws Exception {
        final StdExecutor executor = StdExecutor.create(scheduledPool);

        final NativeExecutable executable = NativeExecutable.of("grep", "hashdfkasf", "a.txt");

        // The stderr of executed command is not empty, and would be outputed to current stderr
        final NativeProcess process = executor.execute(executable).get();
        process.close();
    }

    @Test
    void runGrep_redirect() throws Exception {
        final StdExecutor executor = StdExecutor.create(scheduledPool);

        final NativeExecutable executable = NativeExecutable.builder()
                .command("grep")
                .addOpt("jfjasfkjasf")
                .addOpt("hajkdfhsadhf")
                .stdoutToFile(true)
                .stderrToFile(true)
                .build();

        final NativeProcess process = executor.execute(executable).get();
        process.close();
    }
}