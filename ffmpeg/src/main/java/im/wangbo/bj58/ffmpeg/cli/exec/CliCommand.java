package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import im.wangbo.bj58.ffmpeg.common.Arg;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import javax.annotation.concurrent.Immutable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Immutable
public final class CliCommand {

    private static final File PWD = new File(".").getAbsoluteFile();

    private final ImmutableList<String> fullArgs;
    private final ImmutableMap<String, String> env;

    private final File workingDir;
    private final boolean splitStdout;
    private final boolean splitStderr;

    public static CliCommand of(final String exe, final String... opts) {
        return of(exe, Arrays.asList(opts));
    }

    public static CliCommand of(final String exe, final List<String> opts) {
        return new CliCommand(exe, opts, PWD, ImmutableMap.of(), false, false);
    }

    private CliCommand(
        final String exe, final List<String> opts,
        final File workingDir,
        final Map<String, String> env,
        final boolean splitStdout, final boolean splitStderr
    ) {
        this.fullArgs = ImmutableList.<String>builder().add(exe).addAll(opts).build();
        this.env = ImmutableMap.copyOf(env);
        this.workingDir = workingDir;
        this.splitStdout = splitStdout;
        this.splitStderr = splitStderr;
    }

    public final String command() {
        return fullArgs.get(0);
    }

    public final List<String> args() {
        final int size = fullArgs.size();
        return fullArgs.subList(1, size);
    }

    public final List<String> commandAndArgs() {
        return fullArgs;
    }

    public final Optional<File> workingDir() {
        return Optional.of(workingDir).filter(File::isDirectory);
    }

    public final boolean splitStdout() {
        return splitStdout;
    }

    public final boolean splitStderr() {
        return splitStderr;
    }

    public final CompletionStage<RunningProcess> start(final Executor executor) {

        final ProcessBuilder processBuilder = new ProcessBuilder(fullArgs);

        workingDir().ifPresent(processBuilder::directory);

        processBuilder.environment().putAll(env);

        // for stdout redirect
        if (!splitStdout()) {
            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        }
        // for stderr redirect
        if (!splitStderr()) {
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
        }

        final CompletableFuture<RunningProcess> started = new CompletableFuture<>();
        executor.execute(() -> {
            final Process p;
            try {
                p = processBuilder.start();
                started.complete(new RunningProcess(p));
            } catch (Exception ex) {
                started.completeExceptionally(CliStartingException.create(this, ex));
            }
        });
        return started;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String fullExe;
        private List<String> opts = Lists.newArrayList();

        private Map<String, String> env = Maps.newHashMap();

        private File workingDir = PWD;

        private boolean splitStdout = false;
        private boolean splitStderr = false;

        public Builder command(final String cmd) {
            this.fullExe = cmd;
            return this;
        }

        public Builder addArgs(final List<Arg> args) {
            args.forEach(this::addArg);
            return this;
        }

        public Builder addArg(final Arg arg) {
            opts.add(arg.name());
            arg.value().ifPresent(v -> opts.add(v.asString()));
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

        public Builder addEnv(final Map<String, String> env) {
            this.env.putAll(env);
            return this;
        }

        public Builder addEnv(final String k, final String v) {
            this.env.put(k, v);
            return this;
        }

        public Builder splitStdout(final boolean v) {
            this.splitStdout = v;
            return this;
        }

        public Builder splitStderr(final boolean v) {
            this.splitStderr = v;
            return this;
        }

        public Builder workingDirectory(final File dir) {
            this.workingDir = dir;
            return this;
        }

        public CliCommand build() {
            Preconditions.checkNotNull(fullExe, "command should not be null but was");

            return new CliCommand(fullExe, opts, workingDir, envs, splitStdout, splitStderr);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CliCommand.class.getSimpleName() + "[", "]")
            .add("fullArgs=" + fullArgs)
            .add("workingDir=" + workingDir)
            .add("splitStdout=" + splitStdout)
            .add("splitStderr=" + splitStderr)
            .toString();
    }
}
