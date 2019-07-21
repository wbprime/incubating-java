package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import im.wangbo.bj58.ffmpeg.cli.arg.Arg;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Immutable
public final class CliCommand {

    private static final File PWD = new File(".").getAbsoluteFile();
    private static final CliPidGeneratingStrategy PID_STRATEGY = CliPidGeneratingStrategy.seqBased();

    private final String exec;
    private final ImmutableList<String> fullArgs;
    private final ImmutableMap<String, String> env;

    private final File workingDir;

    private final Clock workingClock;

    public static CliCommand of(final String exe, final String... opts) {
        return of(exe, Arrays.asList(opts));
    }

    public static CliCommand of(final String exe, final List<String> opts) {
        return new CliCommand(exe, opts, Optional.of(PWD), ImmutableMap.of());
    }

    private CliCommand(
        final String exe, final List<String> args,
        final Optional<File> workingDir,
        final Map<String, String> env
    ) {
        this(exe, args, workingDir, env, Clock.systemUTC());
    }

    private CliCommand(
        final String exe, final List<String> args,
        final Optional<File> workingDir,
        final Map<String, String> env,
        final Clock clock
    ) {
        this.exec = exe;
        this.fullArgs = ImmutableList.<String>builder().add(exe).addAll(args).build();
        this.env = ImmutableMap.copyOf(env);
        this.workingDir = workingDir.filter(File::isDirectory).orElse(PWD);
        this.workingClock = clock;
    }

    public final String command() {
        return exec;
    }

    public final List<String> commandLines() {
        return fullArgs;
    }

    public final List<String> args() {
        return ImmutableList.copyOf(Iterables.skip(fullArgs, 1));
    }

    public final File workingDir() {
        return workingDir;
    }

    public final CompletionStage<CliRunningProcess> start(final ScheduledExecutorService executor) {
        return start(executor, PID_STRATEGY);
    }

    public final CompletionStage<CliRunningProcess> start(
        final Executor executor,
        final CliPidGeneratingStrategy pidStrategy) {

        final CliCommand cli = this;

        final String processId = pidStrategy.get();
        final ProcessBuilder processBuilder = new ProcessBuilder(fullArgs);

        processBuilder.environment().putAll(env);

        final File pwd = workingDir();
        processBuilder.directory(pwd);

        // for stderr redirect
        final File stderrFile = new File(pwd, "tmp." + processId + ".stderr." + workingClock.millis());
        processBuilder.redirectError(stderrFile);

        // for stdout redirect
        final File stdoutFile = new File(pwd, "tmp." + processId + ".stdout." + workingClock.millis());
        processBuilder.redirectOutput(stdoutFile);

        return CompletableFuture.supplyAsync(
            () -> {
                final Process p;
                try {
                    p = processBuilder.start();
                    return new CliRunningProcess(cli, processId, p, stdoutFile, stderrFile, workingClock);
                } catch (Exception ex) {
                    stderrFile.delete();
                    stdoutFile.delete();
                    throw CliStartingException.create(this, ex);
                }
            }, executor
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String fullExe;
        private List<String> opts = Lists.newArrayList();

        private Map<String, String> env = Maps.newHashMap();

        @Nullable
        private File workingDir = PWD;

        public Builder command(final String cmd) {
            this.fullExe = cmd;
            return this;
        }

        public Builder addArgs(final List<? extends Arg> args) {
            args.forEach(this::addArg);
            return this;
        }

        public Builder addArg(final Arg arg) {
            arg.collect(opts);
            return this;
        }

        public Builder addArg(final String k, final String v) {
            opts.add(k);
            opts.add(v);
            return this;
        }

        public Builder addArg(final String arg) {
            opts.add(arg);
            return this;
        }

        public Builder addEnvironments(final Map<String, String> env) {
            this.env.putAll(env);
            return this;
        }

        public Builder addEnvironment(final String k, final String v) {
            this.env.put(k, v);
            return this;
        }

        public Builder workingDirectory(@Nullable final File dir) {
            this.workingDir = dir;
            return this;
        }

        public CliCommand build() {
            Preconditions.checkNotNull(fullExe, "command should not be null but was");

            return new CliCommand(fullExe, opts, Optional.ofNullable(workingDir), env);
        }
    }

    void collectMultiLineString(final StringBuilder sb) {
        sb.append('"').append(command()).append('"').append(System.lineSeparator());

        final List<String> args = args();
        if (args.isEmpty()) {
            sb.append("\twith no args").append(System.lineSeparator());
        } else {
            sb.append("\twith args:").append(System.lineSeparator());
            args.forEach(arg -> sb.append("\t  \"").append(arg).append('"').append(System.lineSeparator()));
        }

        if (env.isEmpty()) {
            sb.append("\twith no environments").append(System.lineSeparator());
        } else {
            sb.append("\twith environments:").append(System.lineSeparator());
            env.forEach(
                (k, v) -> sb.append("\t  \"").append(k).append("\" => \"")
                    .append(v).append("\"").append(System.lineSeparator())
            );
        }
        sb.append("\twith working directory: \"").append(workingDir()).append('"');
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getName());
        sb.append(' ');
        collectMultiLineString(sb);
        return sb.toString();
    }
}
