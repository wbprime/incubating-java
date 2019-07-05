package im.wangbo.bj58.ffmpeg.cli.executor;

import im.wangbo.bj58.ffmpeg.cli.executor.ExecutorPool.ExecutableOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.OptionalInt;
import java.util.StringJoiner;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class RunningProcess {

    private final String runningId;

//    private final Path pathToExe;
//    private final List<Arg> args;

    private final Process process;

    private final ExecutableOptions options;

    private byte[] cachedStdout;
    private byte[] cachedStderr;

    RunningProcess(
        final String runningId,
        final Process pb,
        final ExecutableOptions options
    ) {
        this.runningId = runningId;
        this.process = pb;
        this.options = options;
    }

    public final String runningId() {
        return this.runningId;
    }

    final ExecutableOptions startingOptions() {
        return options;
    }

    final InputStream stdout() {
        return process.getInputStream();
    }

    final InputStream stderr() {
        return process.getErrorStream();
    }

    final OptionalInt exitCode() {
        try {
            return OptionalInt.of(process.exitValue());
        } catch (IllegalThreadStateException ex) {
            return OptionalInt.empty();
        }
    }

    final void cacheOutput() throws IOException {
        {
            final InputStream stream = process.getInputStream();
            if (null != stream) {
                int n;
                while ((n = stream.available()) > 0) {
                    final byte[] tmp = new byte[n];
                    final int read = stream.read(tmp);

                    if (read > 0) {
                        appendCachedStdout(tmp, read);
                    } else {
                        break;
                    }
                }
            }
        }
        {
            final InputStream stream = process.getErrorStream();
            if (null != stream) {
                int n;
                while ((n = stream.available()) > 0) {
                    final byte[] tmp = new byte[n];
                    final int read = stream.read(tmp);

                    if (read > 0) {
                        appendCachedStderr(tmp, read);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private synchronized void appendCachedStdout(final byte[] tmp, int read) {
        if (null == cachedStdout) {
            cachedStdout = new byte[tmp.length * 2];
        }



    }

    final synchronized void cacheStdout(final byte[] bytes) {
        cachedStdout.append
    }

    final synchronized void cacheStderr(final byte[] bytes) {
        cachedStderr.append
    }

    static class CachedBytes {
        private byte[] cache = {};
        private int tailIdx = 0;

        void cache(final byte[] arr, int len) {
            if ()
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RunningProcess.class.getSimpleName() + "[", "]")
            .add("runningId='" + runningId + "'")
            .toString();
    }
}
