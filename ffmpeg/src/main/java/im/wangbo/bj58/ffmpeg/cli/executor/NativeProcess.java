package im.wangbo.bj58.ffmpeg.cli.executor;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class NativeProcess implements AutoCloseable {
    private final String id;

    private final NativeExecutable executable;

    private final Process process;

    private final Optional<File> stdoutFile;
    private final Optional<File> stderrFile;

    NativeProcess(final String id, final NativeExecutable executable, final Process pb, final Optional<File> stdout, final Optional<File> stderr) {
        this.id = id;
        this.executable = executable;
        this.process = pb;
        this.stdoutFile = stdout;
        this.stderrFile = stderr;
    }

    public final String pid() {
        return this.id;
    }

    public final List<String> stderrLines() {
        return stderrFile.map(this::lines).orElse(ImmutableList.of());
    }

    public final List<String> stdoutLines() {
        return stdoutFile.map(this::lines).orElse(ImmutableList.of());
    }

    private List<String> lines(final File f) {
        if (null == f) return Collections.emptyList();

        final CharSource s = Files.asCharSource(f, StandardCharsets.UTF_8);
        try {
            return s.readLines();
        } catch (IOException ex) {
            // TODO add more exception handler
            return Collections.emptyList();
        }
    }

    public final NativeExecutable executable() {
        return executable;
    }

    final OptionalInt exitCode() {
        try {
            return OptionalInt.of(process.exitValue());
        } catch (IllegalThreadStateException ex) {
            return OptionalInt.empty();
        }
    }

    @Override
    public void close() {
        stop();
    }

    private void stop() {
        if (process.isAlive()) {
            process.destroy();
        }
        stdoutFile.ifPresent(File::delete);
        stderrFile.ifPresent(File::delete);
    }
}
