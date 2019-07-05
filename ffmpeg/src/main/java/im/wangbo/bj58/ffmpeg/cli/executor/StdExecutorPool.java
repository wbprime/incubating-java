package im.wangbo.bj58.ffmpeg.cli.executor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.io.File;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-02 by Elvis Wang
 */
class StdExecutorPool implements ExecutorPool {

    private final String poolName;

    private final ExecutorPoolOptions options;

    private final AtomicLong processNumber = new AtomicLong(0L);

    private final ConcurrentMap<String, RunningProcess> runningProcesses = Maps.newConcurrentMap();

    private StdExecutorPool(final String name, final ExecutorPoolOptions options) {
        this.poolName = name;
        this.options = options;
    }

    static StdExecutorPool create(final ExecutorPoolOptions opts) {
        return new StdExecutorPool(UUID.randomUUID().toString(), opts);
    }

    @Override
    public CompletionStage<RunningProcess> execute(final ExecutableOptions opts) {
        return start(nextRunningId(), options, opts);
    }

    @Override
    public CompletionStage<TerminatedProcess> awaitTerminated(final RunningProcess process) {
        return submitWatchdogTask(process, options);
    }

    private String nextRunningId() {
        return poolName + '.' + processNumber.getAndIncrement();
    }

    /**
     * Start executing target executable and return the starting result.
     *
     * The executable is started, but may not finished.
     *
     * @return a {@link CompletableFuture} of {@link RunningProcess} instance
     */
    private CompletableFuture<RunningProcess> start(
        final String pid,
        final ExecutorPoolOptions poolOpts,
        final ExecutableOptions exeOpts
    ) {
        final ExecutorService pool = poolOpts.processThreadPool();

        final ProcessBuilder processBuilder;
        {
            final ImmutableList.Builder<String> fullArgsBuilder = ImmutableList.builder();
            fullArgsBuilder.add(exeOpts.command());
            exeOpts.args().forEach(arg -> {
                fullArgsBuilder.add(arg.spec().name());
                arg.value().ifPresent(v -> fullArgsBuilder.add(v.asString()));
            });
            processBuilder = new ProcessBuilder(fullArgsBuilder.build());
        }

        final Optional<File> workingDir = Optional.ofNullable(exeOpts.workingDir());

        workingDir.ifPresent(processBuilder::directory);

        // for stdout redirect
        if (exeOpts.redirectStdout()) {
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        }
        // for stderr redirect
        if (exeOpts.redirectStderr()) {
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        }

        final CompletableFuture<RunningProcess> started = new CompletableFuture<>();
        pool.submit(() -> {
            final Process p;
            try {
                p = processBuilder.start();
                started.complete(new RunningProcess(pid, p, exeOpts));
            } catch (Exception ex) {
                started.completeExceptionally(ExecutorException.startingFailed(exeOpts, ex));
            }
        });
        return started;
    }

    /**
     * Add target process to watch dog to monitor finish state.
     *
     * @param process process
     * @return running state (exit with code or exception)
     */
    private CompletionStage<Void> submitIoTask(
        final RunningProcess process,
        final ExecutorPoolOptions poolOpts
    ) {
        final ScheduledExecutorService pool = poolOpts.scheduledThreadPool();

        final CompletableFuture<TerminatedProcess> terminated = new CompletableFuture<>();

        final ScheduledFuture<?> scheduledFuture = pool.scheduleWithFixedDelay(
            () -> {
                try {
                    final OptionalInt exitCode = process.exitCode();
                    if (exitCode.isPresent()) {
                        // Process finished successfully or failed
                        terminated.complete(
                            new TerminatedProcess(process.runningId(), exitCode.getAsInt(), "",
                                "")
                        );
                    }
                } catch (RuntimeException rex) {
                    // Unexpected errors
                    terminated.completeExceptionally(rex);
                }
            },
            0L, poolOpts.processCheckIntervalInMillis(), TimeUnit.MILLISECONDS
        );
        runningProcesses.put(process.runningId(), process);

    }

    /**
     * Add target process to watch dog to monitor finish state.
     *
     * @param process process
     * @return running state (exit with code or exception)
     */
    private CompletionStage<TerminatedProcess> submitWatchdogTask(
        final RunningProcess process,
        final ExecutorPoolOptions poolOpts
    ) {
        final ScheduledExecutorService pool = poolOpts.scheduledThreadPool();

        final CompletableFuture<TerminatedProcess> terminated = new CompletableFuture<>();

        final ScheduledFuture<?> scheduledFuture = pool.scheduleAtFixedRate(
            () -> {
                try {
                    final OptionalInt exitCode = process.exitCode();
                    if (exitCode.isPresent()) {
                        // Process finished successfully or failed
                        terminated.complete(
                            new TerminatedProcess(process.runningId(), exitCode.getAsInt(), "",
                                "")
                        );
                    }
                } catch (RuntimeException rex) {
                    // Unexpected errors
                    terminated.completeExceptionally(rex);
                }
            },
            0L, poolOpts.processCheckIntervalInMillis(), TimeUnit.MILLISECONDS
        );
        runningProcesses.put(process.runningId(), process);

        return terminated.whenComplete(
            (p, ex) -> {
                runningProcesses.remove(process.runningId());
                scheduledFuture.cancel(true);
            }
        );
    }
}
