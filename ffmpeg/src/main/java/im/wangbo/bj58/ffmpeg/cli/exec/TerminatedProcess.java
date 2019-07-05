package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class TerminatedProcess {

    private final String id;

    private final int exitCode;

    private final Optional<String> stdoutStr;
    private final Optional<String> stderrStr;

    TerminatedProcess(
        final String id, final int exitCode,
        @Nullable final String stdout,
        @Nullable final String stderr
    ) {
        this.id = id;
        this.exitCode = exitCode;
        this.stdoutStr = Optional.ofNullable(stdout);
        this.stderrStr = Optional.ofNullable(stderr);
    }

    public final String runningId() {
        return this.id;
    }

    public final Optional<String> stdout() {
        return stdoutStr;
    }

    public final Optional<String> stderr() {
        return stderrStr;
    }

    public final Optional<List<String>> stdoutLines(final String sep) {
        final Splitter splitter = Splitter.on(sep);
        return stdoutStr.map(splitter::splitToList);
    }

    public final Optional<List<String>> stderrLines(final String sep) {
        final Splitter splitter = Splitter.on(sep);
        return stderrStr.map(splitter::splitToList);
    }

    public int exitCode() {
        return exitCode;
    }

    public boolean isSuccessful() {
        return exitCode == 0;
    }
}
