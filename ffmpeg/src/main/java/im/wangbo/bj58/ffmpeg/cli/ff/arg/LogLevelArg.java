package im.wangbo.bj58.ffmpeg.cli.ff.arg;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
@AutoValue
public abstract class LogLevelArg implements FfArg {
    @Override
    public final String name() {
        return "-loglevel";
    }

    @Override
    public final Optional<String> value() {
        return Optional.of(logLevel().str);
    }

    @Override
    public final String description() {
        return "Set logging level";
    }

    public abstract LogLevel logLevel();

    public static LogLevelArg of(final LogLevel logLevel) {
        return new AutoValue_LogLevelArg(logLevel);
    }

    public enum LogLevel {
        // 'quiet, -8'   Show nothing at all; be silent.
        QUIET("quiet", -8),
        // 'panic, 0'    Only show fatal errors which could lead the process to crash,
        //                  such as an assertion failure. This is not currently used for anything.
        PANIC("panic", 0),
        // 'fatal, 8'    Only show fatal errors. These are errors after which the process
        //                  absolutely cannot continue.
        FATAL("fatal", 8),
        // 'error, 16'   Show all errors, including ones which can be recovered from.
        ERROR("error", 16),
        // 'warning, 24' Show all warnings and errors. Any message related to possibly
        //                  incorrect or unexpected events will be shown.
        WARNING("warning", 24),
        // 'info, 32'    Show informative messages during processing. This is in addition
        //                  to warnings and errors. This is the default value.
        INFO("info", 32),
        // 'verbose, 40' Same as info, except more verbose.
        VERBOSE("verbose", 40),
        // 'debug, 48'   Show everything, including debugging information.
        DEBUG("debug", 48),
        // 'trace, 56'
        TRACE("trace", 56),
        ;

        private final String str;
        private final int code;

        LogLevel(final String str, final int code) {
            this.str = str;
            this.code = code;
        }
    }
}
