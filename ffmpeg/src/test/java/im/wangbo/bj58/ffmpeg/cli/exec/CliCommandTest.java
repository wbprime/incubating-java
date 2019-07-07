package im.wangbo.bj58.ffmpeg.cli.exec;

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
class CliCommandTest {
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
    void runEcho() throws Exception {
        final CliCommand executable = CliCommand.builder()
                .command("echo")
                .addArg("jfjasfkjasf")
                .addArg("hajkdfhsadhf")
                .build();

        final RunningProcess process = executable.start(scheduledPool)
            .toCompletableFuture().get();

//        process.close();
    }

    @Test
    void runCommand_false_failed() throws Exception {
        final CliCommand executable = CliCommand.of("false");

        final RunningProcess process = executable.start(scheduledPool)
                .toCompletableFuture().get();
        process.close();
    }
}
