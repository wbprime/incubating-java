package im.wangbo.bj58.ffmpeg.executor;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class NativeExecutable {
    private final ImmutableList<String> fullArgs;

    @Nullable
    private final File workingDir;
    private final boolean redirectStdout;
    private final boolean redirectStderr;

    public static NativeExecutable of(final String exe, final String ... opts) {
        return of(exe, Arrays.asList(opts));
    }

    public static NativeExecutable of(final String exe, final List<String> opts) {
        return new NativeExecutable(exe, opts, null, false, false);
    }

    private NativeExecutable(
            final String exe, final List<String> opts,
            @Nullable final File workingDir,
            final boolean redirectStdout, final boolean redirectStderr
    ) {
        this.fullArgs = ImmutableList.<String>builder().add(exe).addAll(opts).build();
        this.workingDir = workingDir;
        this.redirectStdout = redirectStdout;
        this.redirectStderr = redirectStderr;
    }

    public final String command() {
        return fullArgs.get(0);
    }

    public final List<String> options() {
        final int size = fullArgs.size();
        return fullArgs.subList(1, size);
    }

    public final List<String> exec() {
        return fullArgs;
    }

    public final Optional<File> workingDir() {
        return Optional.ofNullable(workingDir).filter(File::isDirectory);
    }

    public final boolean redirectsStdout() {
        return redirectStdout;
    }

    public final boolean redirectsStderr() {
        return redirectStderr;
    }

    @Override
    public String toString() {
        return "NativeExecutable{" +
                "fullArgs=" + fullArgs +
                ", workingDir=" + workingDir +
                ", redirectStdout=" + redirectStdout +
                ", redirectStderr=" + redirectStderr +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String fullExe;
        private List<String> opts = Lists.newArrayList();

        @Nullable
        private File workingDir;
        private boolean stdoutToFile = false;
        private boolean stderrToFile = false;

        public Builder command(final String cmd) {
            this.fullExe = cmd;
            return this;
        }

        public Builder addOpts(final List<String> strs) {
            opts.addAll(strs);
            return this;
        }

        public Builder addOpt(final String str) {
            opts.add(str);
            return this;
        }

        public Builder addOpt(final String k, final String v) {
            opts.add(k);
            opts.add(v);
            return this;
        }

        public Builder stdoutToFile(final boolean v) {
            this.stdoutToFile = v;
            return this;
        }

        public Builder stderrToFile(final boolean v) {
            this.stderrToFile = v;
            return this;
        }

        public Builder stdoutToFile() {
            return stdoutToFile(true);
        }

        public Builder stderrToFile() {
            return stderrToFile(true);
        }

        public Builder workingDir(final String f) {
            return workingDir(new File(f));
        }

        public Builder workingDir(final File d) {
            this.workingDir = d;
            return this;
        }

        public NativeExecutable build() {
            Preconditions.checkNotNull(fullExe, "command should not be null but was");

            return new NativeExecutable(fullExe, opts, workingDir, stdoutToFile, stderrToFile);
        }
    }
}
