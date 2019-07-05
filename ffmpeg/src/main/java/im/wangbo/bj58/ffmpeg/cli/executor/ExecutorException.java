package im.wangbo.bj58.ffmpeg.cli.executor;

import im.wangbo.bj58.ffmpeg.cli.executor.ExecutorPool.ExecutableOptions;
import java.util.Collections;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class ExecutorException extends Exception {
    private final int errCode;
    private final List<String> errLines;

    private ExecutorException(final String msg, final int code, final List<String> lines) {
        super(msg);
        this.errCode = code;
        this.errLines = lines;
    }

    static ExecutorException error(final int code, final NativeProcess process) {
        final List<String> args = process.executable().exec();

        final List<String> errLines = process.stderrLines();

        final StringBuilder sb = new StringBuilder("Execution of ").append(args.get(0))
                .append("failed with code ").append(code);

        if (!errLines.isEmpty()) {
            sb.append(", detail: ").append(errLines.get(0)).append(" ... and more ...");
        }
        sb.append(System.lineSeparator());

        sb.append("\tFull command args:").append(System.lineSeparator());
        args.forEach(str -> sb.append("\t  ").append(str).append(System.lineSeparator()));

        return new ExecutorException(sb.toString(), code, process.stderrLines());
    }

    public int errorCode() {
        return errCode;
    }

    public List<String> errorMessages() {
        return null == errLines ? Collections.emptyList() : errLines;
    }

    static Exception startingFailed(final ExecutableOptions opts, final Throwable ex) {
        return ExeStartingException.create(opts, ex);
    }

    static Exception runningFailed(final int errorCode, final ExecutableOptions opts) {
        return ExeRunningException.create(opts, ex);
    }
}
