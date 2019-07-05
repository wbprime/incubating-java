package im.wangbo.bj58.ffmpeg.cli.executor;

import im.wangbo.bj58.ffmpeg.cli.executor.ExecutorPool.ExecutableOptions;
import im.wangbo.bj58.ffmpeg.common.Arg;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class ExeRunningException extends Exception {

    private ExeRunningException(final String msg) {
        super(msg);
    }

    static ExeRunningException create(final int code, final ExecutableOptions opts) {
        final StringBuilder sb = new StringBuilder("Failed to run ");
        sb.append('"').append(opts.command()).append('"');

        sb.append(", exiting with code ").append(code);

        final List<Arg> args = opts.args();
        if (!args.isEmpty()) {
            sb.append("\twith args:").append(System.lineSeparator());
            args.forEach(
                arg -> {
                    sb.append("\t  Arg \"").append(arg.spec().name()).append("\" => ");
                    arg.value().ifPresent(v -> v.describeTo(sb));
                    sb.append(System.lineSeparator());
                }
            );
        } else {
            sb.append(System.lineSeparator());
        }
        sb.append("\twith working directory: ").append(opts.workingDir());
        sb.append("\twith stdout redirect: ").append(opts.redirectStdout());
        sb.append("\twith stderr redirect: ").append(opts.redirectStderr());

        return new ExeRunningException(sb.toString());
    }
}
