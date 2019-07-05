package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.OptionalInt;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class RunningProcess {

    private final Process process;

    RunningProcess(final Process pb) {
        this.process = pb;
    }

    public InputStream stdout() {
        return process.getInputStream();
    }

    public InputStream stderr() {
        return process.getErrorStream();
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

    public void destroy() {
        process.destroy();
    }

    public CompletionStage<TerminatedProcess> awaitTerminated(
        final ScheduledExecutorService executor,
        final Consumer<String> lineHandler,
        final long aliveChecking,
        final long ioChecking,
        final TimeUnit unit
    ) {
        final CompletableFuture<TerminatedProcess> future = new CompletableFuture<>();

        final RunningProcess p = this;
        final ScheduledFuture<?> aliveFuture = executor.scheduleAtFixedRate(
            () -> {

            }, 0L, aliveChecking, unit
        );

        final InputStream stdout = stdout();
        if (null != stdout) {
            final ScheduledFuture<?> ioFuture = executor.scheduleAtFixedRate(
                new OutputHandler(stdout, lineHandler),
                0L, ioChecking, unit
            );
        }

        final StderrCollector stderrCollector = new StderrCollector();
        final InputStream stderr = stderr();
        if (null != stderr) {
            final Future<?> ioFuture = executor.submit(
                new OutputHandler(stderr, stderrCollector)
            );
        }

        return future.whenComplete(
            (result, ex) -> {
                p.destroy();
                ioFuture.cancel(false);
                aliveFuture.cancel(true);
            }
        );
    }

    static class OutputHandler implements Runnable {

        private final InputStream stream;

        private final Consumer<String> lineHandler;

        OutputHandler(final InputStream stream, final Consumer<String> lineHandler) {
            this.stream = stream;
            this.lineHandler = lineHandler;
        }

        @Override
        public void run() {
            try {
                try (final BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineHandler.accept(line);
                    }
                }

            } catch (IOException ex) {
                // Do nothing, just return
            }
        }
    }

    static class StderrCollector implements Consumer<String> {

        private final List<String> lines = Lists.newArrayList();

        @Override
        public void accept(final String line) {
            lines.add(line);
        }

        final List<String> lines() {
            return lines;
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RunningProcess.class.getSimpleName() + "[", "]")
            .add("process=" + process)
            .toString();
    }
}
