package im.wangbo.bj58.ffmpeg.cli.exec;

import java.util.List;

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
        final StringBuilder sb = new StringBuilder("Failed to start ");
        sb.append('"').append(command.command()).append('"');

        final List<String> args = command.args();
        if (!args.isEmpty()) {
            sb.append("\twith args:").append(System.lineSeparator());
            args.forEach(arg -> sb.append("\t  \"").append(arg).append(System.lineSeparator()));
        } else {
            sb.append(System.lineSeparator());
        }
        sb.append("\twith working directory: ").append(command.workingDir());
        sb.append("\twith stdout split: ").append(command.splitStdout());
        sb.append("\twith stderr split: ").append(command.splitStderr());

        return new CliStartingException(sb.toString(), ex);
    }
}
