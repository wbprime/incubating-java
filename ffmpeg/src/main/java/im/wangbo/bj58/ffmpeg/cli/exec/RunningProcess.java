package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.io.Files;
import org.eclipse.collections.api.set.primitive.ImmutableIntSet;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.OptionalInt;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class RunningProcess implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(RunningProcess.class);

    private final String processId;

    private final CliCommand command;

    private final Process process;

    private final File stdoutFile;
    private final File stderrFile;

    private final Clock workingClock;
    private final Instant startedInstant;

    RunningProcess(
        final CliCommand command,
        final String id, final Process pb,
        final File stdoutFile, final File stderrFile,
        final Clock clock
    ) {
        this.command = command;
        this.processId = id;
        this.process = pb;
        this.stdoutFile = stdoutFile;
        this.stderrFile = stderrFile;
        this.workingClock = clock;
        this.startedInstant = clock.instant();
    }

    public String processId() {
        return processId;
    }

    public CliCommand cli() {
        return command;
    }

    public Instant startedTime() {
        return startedInstant;
    }

    Clock clock() {
        return workingClock;
    }

    File stdoutFile() {
        return stdoutFile;
    }

    public InputStream stdout() throws IOException {
        return Files.asByteSource(stdoutFile).openStream();
    }

    File stderrFile() {
        return stderrFile;
    }

    public InputStream stderr() throws IOException {
        return Files.asByteSource(stderrFile).openStream();
    }

    public OptionalInt exitCode() {
        try {
            return OptionalInt.of(process.exitValue());
        } catch (IllegalThreadStateException ex) {
            // This is safe
            return OptionalInt.empty();
        }
    }

    public int waitFor() throws InterruptedException {
        return process.waitFor();
    }

    public OptionalInt waitFor(final Duration d) throws InterruptedException {
        final boolean exited = process.waitFor(d.toMillis(), TimeUnit.MILLISECONDS);
        return exited ? exitCode() : OptionalInt.empty();
    }

    @Override
    public void close() {
        try {
            process.destroy();
            stdoutFile.delete();
            stderrFile.delete();
        } catch (Exception ex) {
            LOG.warn("Failed to close {}", this, ex);
        }
    }

    public CompletionStage<TerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final int... expectedCodes
    ) {
        return awaitTerminated(executor, null, expectedCodes);
    }

    public CompletionStage<TerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        @Nullable final Consumer<String> lineHandler,
        final int... expectedCodes
    ) {
        final RunningProcess process = this;

        final CompletableFuture<Integer> future = new CompletableFuture<>();

        final ScheduledFuture<?> aliveFuture = executor.scheduleAtFixedRate(
            new AliveChecker(this, future), 0L, 10L, TimeUnit.MILLISECONDS
        );

        final ImmutableIntSet successCodes = IntSets.immutable.of(expectedCodes);
        return future
            .whenComplete(
                (code, ex) -> {
                    LOG.trace("AwaitTerminated future determined, step 1");
//                    aliveFuture.cancel(true);
                }
            )
            .thenCompose(c -> terminateProcess(process, lineHandler, c, successCodes, executor, aliveFuture));
    }

    private CompletableFuture<TerminatedProcess> terminateProcess(
        final RunningProcess process,
        @Nullable final Consumer<String> lineHandler,
        final int code, final ImmutableIntSet successCodes,
        final Executor executor,
        final ScheduledFuture<?> aliveChecking
    ) {
        LOG.trace("AwaitTerminated future determined, step 2");
        final CompletableFuture<TerminatedProcess> future = new CompletableFuture<>();
        executor.execute(
            () -> {
                LOG.trace("AwaitTerminated future determined, step 3");
                if (successCodes.contains(code)) {
                    // Exit code regarded as successful
                    try {
                        if (lineHandler != null) {
                            Files.asCharSource(process.stdoutFile(), StandardCharsets.UTF_8)
                                .forEachLine(lineHandler);
                        }
                        future.complete(TerminatedProcess.create(process, code));
                    } catch (RuntimeException | IOException exx) {
                        future.completeExceptionally(CliRunningException.create(process, exx));
                    }
                } else {
                    // Exit code regarded as failed
                    try {
                        final String err = Files.asCharSource(process.stderrFile(), StandardCharsets.UTF_8)
                            .read();
                        future.completeExceptionally(CliRunningException.create(process, err));
                    } catch (IOException exx) {
                        future.completeExceptionally(CliRunningException.create(process, exx));
                    }
                }

                process.close();
            }
        );

        return future;
    }

    static class AliveChecker implements Runnable {
        private final RunningProcess process;

        private CompletableFuture<Integer> onExit;

        AliveChecker(final RunningProcess process, final CompletableFuture<Integer> future) {
            this.process = process;
            this.onExit = future;
        }

        @Override
        public void run() {
//            LOG.trace("Alive checked ran ...");
            process.exitCode().ifPresent(onExit::complete);
        }
    }

    void collectMultiLineString(final StringBuilder sb) {
        command.collectMultiLineString(sb);
        sb.append("\twith process id: ").append(processId).append(System.lineSeparator())
            .append("\twith started time: ").append(startedInstant).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getName());
        collectMultiLineString(sb);
        return sb.toString();
    }
}
