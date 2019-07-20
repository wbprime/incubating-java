package im.wangbo.bj58.ffmpeg.cli.exec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class CliInterruptedException extends RuntimeException {

    private CliInterruptedException(final String msg) {
        super(msg);
    }

    static CliInterruptedException create(final CliRunningProcess process) {
        final StringBuilder sb = new StringBuilder("Interrupted running of cli command ");
        process.collectMultiLineString(sb);
        sb.append(System.lineSeparator())
            .append("\twith interrupted time: ").append(process.clock().instant())
            .append(System.lineSeparator())
            .append("\twith no stderr lines");
        return new CliInterruptedException(sb.toString());
    }

    static CliInterruptedException create(final CliRunningProcess process, final String err) {
        final StringBuilder sb = new StringBuilder("Interrupted running of cli command ");
        process.collectMultiLineString(sb);
        sb.append(System.lineSeparator())
            .append("\twith interrupted time: ").append(process.clock().instant())
            .append(System.lineSeparator())
            .append("\twith stderr lines: ").append(System.lineSeparator()).append(err);
        return new CliInterruptedException(sb.toString());
    }
}
