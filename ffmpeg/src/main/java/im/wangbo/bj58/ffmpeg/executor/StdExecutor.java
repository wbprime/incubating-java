package im.wangbo.bj58.ffmpeg.executor;

import com.google.common.collect.Maps;

import java.io.File;
import java.time.Clock;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongFunction;

import static im.wangbo.bj58.ffmpeg.executor.FilenameGeneratingStrategy.paddingTimestamp;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class StdExecutor {
    private static final LongFunction<String> FALLBACK_PID_GENERATOR = String::valueOf;
    private static final long FALLBACK_CHECK_MILLIS = 100L;
    private static final int FALLBACK_MAX_FILENAME_COUNT = 5;

    // To run foreground process tasks
    private final ExecutorService foregroundPool;
    // To run background process status check tasks
    private final ScheduledExecutorService backgroundPool;

    private final long checkInterval;

    // To generate a sequence of process id
    private final LongFunction<String> pidGenerator;

    private final FilenameGeneratingStrategy stdoutFilenameStrategy;
    private final FilenameGeneratingStrategy stderrFilenameStrategy;
    private final int maxFilenameCount;

    private final ConcurrentMap<String, ScheduledFuture<?>> watchDogTasks = Maps.newConcurrentMap();

    private final AtomicLong id = new AtomicLong(0L);

    public static StdExecutor create(final ScheduledExecutorService pool) {
        return new StdExecutor(pool, pool, FALLBACK_PID_GENERATOR,
                paddingTimestamp("stdout", Clock.systemDefaultZone()),
                paddingTimestamp("stderr", Clock.systemDefaultZone()),
                FALLBACK_MAX_FILENAME_COUNT,
                FALLBACK_CHECK_MILLIS);
    }

    private StdExecutor(
            final ExecutorService pool1,
            final ScheduledExecutorService pool2,
            final LongFunction<String> pidGenerator,
            final FilenameGeneratingStrategy stdoutFilenameStrategy,
            final FilenameGeneratingStrategy stderrFilenameStrategy,
            final int maxFilenameCount,
            final long checkIntervalInMillis
    ) {
        this.foregroundPool = pool1;
        this.backgroundPool = pool2;
        this.pidGenerator = pidGenerator;
        this.stdoutFilenameStrategy = stdoutFilenameStrategy;
        this.stderrFilenameStrategy = stderrFilenameStrategy;
        this.maxFilenameCount = maxFilenameCount;
        this.checkInterval = checkIntervalInMillis;
    }

    /**
     * Start executing target executable and return the starting result.
     *
     * @param exe target executable
     * @return a {@link CompletableFuture} of {@link NativeProcess} instance
     */
    public CompletionStage<NativeProcess> execute(final NativeExecutable exe) {
        final String pid = pidGenerator.apply(id.getAndIncrement());

        return start(foregroundPool, pid, exe)
                .thenComposeAsync(this::attachToWatchDog)
                .whenCompleteAsync((re, ex) -> detachFromWatchDog(re), foregroundPool);
    }

    private void detachFromWatchDog(final NativeProcess process) {
        final ScheduledFuture<?> scheduledFuture = watchDogTasks.remove(process.pid());
        if (null != scheduledFuture) {
            scheduledFuture.cancel(true);
        }
    }

    /**
     * Add target process to watch dog to monitor finish state.
     *
     * @param process process
     * @return running state (exit with code or exception)
     */
    private CompletionStage<NativeProcess> attachToWatchDog(final NativeProcess process) {
        final CompletableFuture<NativeProcess> watching = new CompletableFuture<>();

        final ScheduledFuture<?> scheduledFuture = backgroundPool.scheduleAtFixedRate(
                () -> {
                    try {
                        final OptionalInt exitCode = process.exitCode();
                        if (exitCode.isPresent()) {
                            if (exitCode.getAsInt() == 0) {
                                // Process finished successfully
                                watching.complete(process);
                            } else {
                                // Process finished exceptionally
                                watching.completeExceptionally(
                                        ExecutorException.error(exitCode.getAsInt(), process)
                                );
                            }
                        }
                    } catch (RuntimeException rex) {
                        // Unexpected errors
                        watching.completeExceptionally(rex);
                    }
                }, checkInterval, checkInterval, TimeUnit.MILLISECONDS
        );
        watchDogTasks.put(process.pid(), scheduledFuture);

        return watching;
    }

    /**
     * Start executing target executable and return the starting result.
     *
     * The executable is started, but may not finished.
     *
     * @return a {@link CompletableFuture} of {@link NativeProcess} instance
     */
    private CompletableFuture<NativeProcess> start(
            final ExecutorService pool,
            final String pid,
            final NativeExecutable executable
    ) {
        final ProcessBuilder processBuilder = new ProcessBuilder(executable.exec());

        final Optional<File> workingDir = executable.workingDir();

        workingDir.ifPresent(processBuilder::directory);

        // for stdout redirect
        final Optional<File> stdoutFile = generateOutputFile(executable.redirectsStdout(), workingDir,
                stdoutFilenameStrategy, maxFilenameCount, pid);
        // for stderr redirect
        final Optional<File> stderrFile = generateOutputFile(executable.redirectsStderr(), workingDir,
                stderrFilenameStrategy, maxFilenameCount, pid);

        // stdout
        if (stderrFile.isPresent()) {
            processBuilder.redirectError(stderrFile.get());
        } else {
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        }
        // stderr
        if (stdoutFile.isPresent()) {
            processBuilder.redirectOutput(stdoutFile.get());
        } else {
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        }

        final CompletableFuture<NativeProcess> started = new CompletableFuture<>();
        pool.submit(() -> {
            final Process p;
            try {
                p = processBuilder.start();
            } catch (Exception ex) {
                started.completeExceptionally(ex);
                return;
            }

            final NativeProcess process = new NativeProcess(pid, executable, p, stdoutFile, stderrFile);

            started.complete(process);
        });
        return started;
    }

    private static Optional<File> generateOutputFile(
            final boolean generates, final Optional<File> dir,
            final FilenameGeneratingStrategy filenameGeneratingStrategy, final int maxAttempts,
            final String pid
    ) {
        if (generates) {
            final String dirNameWithSlash = dir.map(File::getPath).map(s -> s + File.separator).orElse("");

            int n = 0;
            File f;
            do {
                // Generate a filename not exists
                f = new File(dirNameWithSlash + filenameGeneratingStrategy.apply(pid));

                if (n >= maxAttempts) {
                    // TODO throw exception
                    return Optional.empty();
                }
                n++;
            } while (f.exists());

            return Optional.of(f);
        } else {
            return Optional.empty();
        }
    }
}
