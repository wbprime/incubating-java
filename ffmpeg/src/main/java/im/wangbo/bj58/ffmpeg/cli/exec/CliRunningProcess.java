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
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static im.wangbo.bj58.ffmpeg.cli.exec.CliProcessTimeoutingStrategy.unlimited;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class CliRunningProcess implements AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(CliRunningProcess.class);

    private final String processId;

    private final CliCommand command;

    private final Process process;

    private final File stdoutFile;
    private final File stderrFile;

    private final Clock workingClock;
    private final Instant startedInstant;

    CliRunningProcess(
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

    final void destroy() {
        try {
            process.destroy();
        } catch (Exception ex) {
            LOG.warn("Failed to destroy {}", this, ex);
        }
    }

    @Override
    public synchronized void close() {
        this.destroy();
        stdoutFile.delete();
        stderrFile.delete();
    }

    public CompletionStage<CliTerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final int... expectedCodes
    ) {
        return awaitTerminated(executor, unlimited(), expectedCodes);
    }

    public CompletionStage<CliTerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final CliProcessTimeoutingStrategy timeouting,
        final int... expectedCodes
    ) {
        return awaitTerminated(executor, timeouting, null, expectedCodes);
    }

    public CompletionStage<CliTerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final CliProcessTimeoutingStrategy timeouting,
        @Nullable final Consumer<String> lineHandler,
        final int... expectedCodes
    ) {
        return awaitTerminated(executor,
            timeouting,
            CliProcessAliveCheckingStrategy.of(10L, TimeUnit.MILLISECONDS),
            lineHandler, expectedCodes);
    }

    public CompletionStage<CliTerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final CliProcessTimeoutingStrategy timeouting,
        final CliProcessAliveCheckingStrategy aliveChecking,
        @Nullable final Consumer<String> lineHandler,
        final int... expectedCodes
    ) {
        final CliRunningProcess process = this;

        // A future set by alive check or timeout checker
        // If absent, meaning process is timeouted and should be destroyed
        // If present, meaning process is terminated with given exit code
        final CompletableFuture<OptionalInt> future = new CompletableFuture<>();

        final ScheduledFuture<?> aliveChecker = executor.scheduleAtFixedRate(
            new AliveChecker(this, future), 0L, aliveChecking.millis(), TimeUnit.MILLISECONDS
        );
        LOG.trace("{} scheduled for {} every {} millis", AliveChecker.class.getSimpleName(),
            processId(), aliveChecking.millis());

        final ScheduledFuture<?> timeoutChecker = timeouting.millis().isPresent() ?
            executor.schedule(
                new TimeoutChecker(this, future), timeouting.millis().getAsLong(),
                TimeUnit.MILLISECONDS
            ) : null;
        timeouting.millis().ifPresent(
            n -> LOG.trace("{} scheduled for {} after {} millis",
                TimeoutChecker.class.getSimpleName(), processId(), n)
        );

        final ImmutableIntSet successCodes = IntSets.immutable.of(expectedCodes);
        return future
            .whenComplete((re, ex) -> {
                aliveChecker.cancel(true);
                if (null != timeoutChecker) timeoutChecker.cancel(true);
            })
            .thenCompose(c -> terminateProcess(process, lineHandler, c, successCodes, executor));
    }

    private CompletableFuture<CliTerminatedProcess> terminateProcess(
        final CliRunningProcess process,
        @Nullable final Consumer<String> lineHandler,
        final OptionalInt codeOpt, final ImmutableIntSet successCodes,
        final Executor executor
    ) {
        final CompletableFuture<CliTerminatedProcess> future = new CompletableFuture<>();
        executor.execute(
            () -> {
                if (codeOpt.isPresent()) {
                    // Process is terminated from OS
                    final int code = codeOpt.getAsInt();
                    if (successCodes.contains(code)) {
                        // Exit code regarded as successful
                        final File f = process.stdoutFile();
                        if (lineHandler != null && f.canRead()) {
                            try {
                                Files.asCharSource(f, StandardCharsets.UTF_8).forEachLine(lineHandler);
                            } catch (RuntimeException | IOException exx) {
                                LOG.warn("Failed to process stdout file for {}", process.processId, exx);
                            }
                        }
                        future.complete(CliTerminatedProcess.create(process, code));
                    } else {
                        // Exit code regarded as failed
                        final File f = process.stdoutFile();
                        if (f.canRead()) {
                            try {
                                final String err = Files.asCharSource(process.stderrFile(),
                                    StandardCharsets.UTF_8).read();
                                future.completeExceptionally(CliRunningException.create(process, code, err));
                            } catch (IOException exx) {
                                LOG.warn("Failed to process stderr file for {}", process.processId, exx);
                                future.completeExceptionally(CliRunningException.create(process, code));
                            }
                        } else {
                            future.completeExceptionally(CliRunningException.create(process, code));
                        }
                    }
                } else {
                    // Process should be terminated caused by timeout

                    final File f = process.stdoutFile();
                    if (f.canRead()) {
                        try {
                            final String err = Files.asCharSource(process.stderrFile(),
                                StandardCharsets.UTF_8).read();
                            future.completeExceptionally(CliInterruptedException.create(process, err));
                        } catch (IOException exx) {
                            LOG.warn("Failed to process stderr file for {}", process.processId, exx);
                            future.completeExceptionally(CliInterruptedException.create(process));
                        }
                    } else {
                        future.completeExceptionally(CliInterruptedException.create(process));
                    }
                }

                process.close();
            }
        );

        return future;
    }

    static class AliveChecker implements Runnable {
        private final CliRunningProcess process;

        private final CompletableFuture<OptionalInt> onExit;

        AliveChecker(final CliRunningProcess process, final CompletableFuture<OptionalInt> future) {
            this.process = process;
            this.onExit = future;
        }

        @Override
        public void run() {
            LOG.trace("{} ran for {}", getClass().getSimpleName(), process.processId());
            process.exitCode().ifPresent(n -> onExit.complete(OptionalInt.of(n)));
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", AliveChecker.class.getSimpleName() + "[", "]")
                .toString();
        }
    }

    static class TimeoutChecker implements Runnable {
        private final CliRunningProcess process;

        private CompletableFuture<OptionalInt> onExit;

        TimeoutChecker(final CliRunningProcess process, final CompletableFuture<OptionalInt> future) {
            this.process = process;
            this.onExit = future;
        }

        @Override
        public void run() {
            LOG.trace("{} ran for {}", getClass().getSimpleName(), process.processId());
            onExit.complete(OptionalInt.empty());
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", AliveChecker.class.getSimpleName() + "[", "]")
                .toString();
        }
    }

    void collectMultiLineString(final StringBuilder sb) {
        command.collectMultiLineString(sb);
        sb.append(System.lineSeparator())
            .append("\twith process id: ").append(processId).append(System.lineSeparator())
            .append("\twith started time: ").append(startedInstant);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getName());
        collectMultiLineString(sb);
        return sb.toString();
    }
}
