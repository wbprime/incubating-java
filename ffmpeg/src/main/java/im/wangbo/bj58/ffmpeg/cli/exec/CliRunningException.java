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

    static CliRunningException create(final RunningProcess process, final String stderr) {
        return new CliRunningException(stderr);
    }
}
