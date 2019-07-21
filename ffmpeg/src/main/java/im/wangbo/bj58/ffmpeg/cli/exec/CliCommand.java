package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.base.Preconditions;
import im.wangbo.bj58.ffmpeg.cli.arg.Arg;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.io.File;
import java.time.Clock;
import java.util.Arrays;
import java.util.Collections;
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
        return new CliCommand(exe, opts, Optional.of(PWD), Collections.emptyMap());
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
        this.fullArgs = Lists.mutable.of(exe).withAll(args).toImmutable();
        this.env = Maps.immutable.ofMap(env);
        this.workingDir = workingDir.filter(File::isDirectory).orElse(PWD);
        this.workingClock = clock;
    }

    public final String command() {
        return exec;
    }

    public final List<String> commandLines() {
        return fullArgs.castToList();
    }

    public final List<String> args() {
        return fullArgs.drop(1).castToList();
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
        final ProcessBuilder processBuilder = new ProcessBuilder(fullArgs.castToList());

        processBuilder.environment().putAll(env.castToMap());

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
        private MutableList<String> opts = Lists.mutable.empty();

        private MutableMap<String, String> env = Maps.mutable.empty();

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
            env.forEachKeyValue(
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
