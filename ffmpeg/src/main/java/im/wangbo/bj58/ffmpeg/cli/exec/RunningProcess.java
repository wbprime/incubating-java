package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.auto.value.AutoValue;
import com.google.common.base.Throwables;
import com.google.common.io.Files;
import org.eclipse.collections.api.set.primitive.ImmutableIntSet;
import org.eclipse.collections.impl.factory.primitive.IntSets;

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

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class RunningProcess implements AutoCloseable {

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
            return OptionalInt.empty();
        }
    }

    public int waitFor() throws InterruptedException {
        return process.waitFor();
    }

    public OptionalInt waitFor(final long n, final TimeUnit unit) throws InterruptedException {
        final boolean exited = process.waitFor(n, unit);
        return exited ? exitCode() : OptionalInt.empty();
    }

    @Override
    public void close() throws Exception {
        try {
            stdoutFile.delete();
            stderrFile.delete();
            process.destroy();
        } catch (Exception ex) {
            Throwables.throwIfUnchecked(ex);
            // TODO Throw exception here
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
        return future.whenComplete(
            (code, ex) -> {
                aliveFuture.cancel(true);
            }
        ).thenCompose(c -> terminateProcess(process, lineHandler, c, successCodes));
    }

    private CompletableFuture<TerminatedProcess> terminateProcess(
        final RunningProcess process,
        @Nullable final Consumer<String> lineHandler,
        final int code, final ImmutableIntSet successCodes
    ) {
        final CompletableFuture<TerminatedProcess> future = new CompletableFuture<>();
        if (successCodes.contains(code)) {
            // Exit code regarded as successful
            if (lineHandler != null) {
                try {
                    Files.asCharSource(process.stdoutFile(), StandardCharsets.UTF_8)
                        .forEachLine(lineHandler);
                } catch (IOException exx) {
                    future.completeExceptionally(exx); // TODO
                }
            }
            return CompletableFuture.completedFuture(TerminatedProcess.create(process, code));
        } else {
            // Exit code regarded as failed
            try {
                final String err = Files.asCharSource(process.stderrFile(), StandardCharsets.UTF_8)
                    .read();
                future.completeExceptionally(CliRunningException.create(process, err));
            } catch (IOException exx) {
                future.completeExceptionally(exx); // TODO
            }
        }

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
            process.exitCode().ifPresent(onExit::complete);
        }
    }

    @AutoValue
    public static abstract class Options {
        public abstract Duration aliveCheckingInterval();

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RunningProcess.class.getSimpleName() + "[", "]")
            .add("process=" + process)
            .toString();
    }
}
