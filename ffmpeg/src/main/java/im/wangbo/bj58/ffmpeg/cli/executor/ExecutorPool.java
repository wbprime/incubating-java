package im.wangbo.bj58.ffmpeg.cli.executor;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.ffmpeg.common.Arg;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nullable;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-02 by Elvis Wang
 */
public interface ExecutorPool {

    CompletionStage<RunningProcess> execute(final ExecutableOptions opts);

    CompletionStage<TerminatedProcess> awaitTerminated(final RunningProcess process);

    static ExecutorPool create(final ExecutorPoolOptions opts) {
        return StdExecutorPool.create(opts);
    }

    @AutoValue
    abstract class ExecutorPoolOptions {

        public abstract ExecutorService processThreadPool();

        public abstract ScheduledExecutorService scheduledThreadPool();

        public abstract long processCheckIntervalInMillis();
        public abstract long ioCheckIntervalInMillis();
    }

    @AutoValue
    abstract class ExecutableOptions {

        abstract String command();

        abstract ImmutableList<Arg> args();

        @Nullable
        abstract File workingDir();

        abstract boolean redirectStdout();

        abstract boolean redirectStderr();

        public static Builder builder() {
            return new AutoValue_ExecutorPool_ExecutableOptions.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {

            public abstract Builder exePath(final String cmd);

            public abstract Builder exeArgs(final List<Arg> args);

            public abstract Builder workingDir(@Nullable final File workingDir);

            public abstract Builder redirectStdout(boolean redirectStdout);

            public abstract Builder redirectStderr(boolean redirectStderr);

            public abstract ExecutableOptions build();
        }
    }
}
