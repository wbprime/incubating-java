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
        return new CliStartingException("Failed to start " + command.toMultiLineString(), ex);
    }
}
