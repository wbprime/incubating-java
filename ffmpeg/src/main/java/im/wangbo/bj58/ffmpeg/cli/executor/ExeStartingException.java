package im.wangbo.bj58.ffmpeg.cli.executor;

import im.wangbo.bj58.ffmpeg.cli.executor.ExecutorPool.ExecutableOptions;
import im.wangbo.bj58.ffmpeg.common.Arg;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class ExeStartingException extends Exception {

    private ExeStartingException(final String msg, final Throwable ex) {
        super(msg, ex);
    }

    static ExeStartingException create(final ExecutableOptions opts, final Throwable ex) {
        final StringBuilder sb = new StringBuilder("Failed to start ");
        sb.append('"').append(opts.command()).append('"');

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

        return new ExeStartingException(sb.toString(), ex);
    }
}
