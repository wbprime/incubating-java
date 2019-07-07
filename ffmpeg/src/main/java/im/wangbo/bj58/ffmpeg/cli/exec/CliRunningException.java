package im.wangbo.bj58.ffmpeg.cli.exec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class CliRunningException extends Exception {

    private CliRunningException(final String msg) {
        super(msg);
    }

    private CliRunningException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    static CliRunningException create(final RunningProcess process, final Throwable ex) {
        final StringBuilder sb = new StringBuilder("Failed to run cli command ");
        process.collectMultiLineString(sb);
        sb.append("\twith unexpected error: (")
            .append(ex.getClass())
            .append(") \"")
            .append(ex.getMessage())
            .append('"');
        return new CliRunningException(sb.toString(), ex);
    }

    static CliRunningException create(final RunningProcess process, final String stderr) {
        final StringBuilder sb = new StringBuilder("Failed to run cli command ");
        process.collectMultiLineString(sb);
        sb.append("\twith stderr lines: ").append(System.lineSeparator()).append(stderr);
        return new CliRunningException(sb.toString());
    }
}
