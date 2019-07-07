package im.wangbo.bj58.ffmpeg.cli.exec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class CliStartingException extends Exception {

    private CliStartingException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    static CliStartingException create(final CliCommand command, final Throwable ex) {
        final StringBuilder sb = new StringBuilder("Failed to start cli command ");
        command.collectMultiLineString(sb);
        return new CliStartingException(sb.toString(), ex);
    }
}
