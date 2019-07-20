package im.wangbo.bj58.ffmpeg.cli.exec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class CliRunningException extends RuntimeException {

    private CliRunningException(final String msg) {
        super(msg);
    }

    private CliRunningException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    static CliRunningException create(final CliRunningProcess process, final int code) {
        final StringBuilder sb = new StringBuilder("Failed running of cli command ");
        process.collectMultiLineString(sb);
        sb.append(System.lineSeparator())
            .append("\twith exit code ").append(code).append(System.lineSeparator())
            .append("\twith no stderr lines");
        return new CliRunningException(sb.toString());
    }

    static CliRunningException create(final CliRunningProcess process, final int code, final String stderr) {
        final StringBuilder sb = new StringBuilder("Failed to run cli command ");
        process.collectMultiLineString(sb);
        sb.append(System.lineSeparator())
            .append("\twith exit code ").append(code).append(System.lineSeparator())
            .append("\twith stderr lines: ").append(System.lineSeparator()).append(stderr);
        return new CliRunningException(sb.toString());
    }
}
