package im.wangbo.bj58.ffmpeg.cli.exec;

import java.time.Instant;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class TerminatedProcess {

    private final int exitCode;

    private final RunningProcess process;
    private final Instant terminatedInstant;

    static TerminatedProcess create(
        final RunningProcess process,
        final int exitCode
    ) {
        return new TerminatedProcess(process, exitCode, process.clock().instant());
    }

    private TerminatedProcess(
        final RunningProcess process,
        final int exitCode,
        final Instant terminated
    ) {
        this.process = process;
        this.exitCode = exitCode;
        this.terminatedInstant = terminated;
    }

    public String processId() {
        return this.process.processId();
    }

    public Instant startedTime() {
        return this.process.startedTime();
    }

    public Instant terminatedTime() {
        return terminatedInstant;
    }

    public int exitCode() {
        return exitCode;
    }

    void collectMultiLineString(final StringBuilder sb) {
        process.collectMultiLineString(sb);
        sb.append("\twith exited time: ").append(terminatedInstant).append(System.lineSeparator());
        sb.append("\twith exited code: ").append(exitCode).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getName());
        collectMultiLineString(sb);
        return sb.toString();
    }
}
