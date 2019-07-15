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
        return Optional.empty();
    }

    @Override
    public final String description() {
        return "Set logging level";
    }

    public abstract LogLevel logLevel();

    public static LogLevelArg of(final LogLevel logLevel) {
        return new AutoValue_LogLevelArg(logLevel);
    }

    public static LogLevelArg debug() {
        return of(LogLevel.DEBUG);
    }

    public static LogLevelArg info() {
        return of(LogLevel.INFO);
    }

    public static LogLevelArg warning() {
        return of(LogLevel.WARNING);
    }

    public static LogLevelArg error() {
        return of(LogLevel.ERROR);
    }

    public enum LogLevel {
        QUIET(-8), // 'quiet, -8'   Show nothing at all; be silent.
        PANIC(
            0), // 'panic, 0'    Only show fatal errors which could lead the process to crash, such as an assertion failure. This is not currently used for anything.
        FATAL(
            8), // 'fatal, 8'    Only show fatal errors. These are errors after which the process absolutely cannot continue.
        ERROR(16), // 'error, 16'   Show all errors, including ones which can be recovered from.
        WARNING(
            24), // 'warning, 24' Show all warnings and errors. Any message related to possibly incorrect or unexpected events will be shown.
        INFO(
            32), // 'info, 32'    Show informative messages during processing. This is in addition to warnings and errors. This is the default value.
        VERBOSE(40), // 'verbose, 40' Same as info, except more verbose.
        DEBUG(48), // 'debug, 48'   Show everything, including debugging information.
        TRACE(56), // 'trace, 56'
        ;

        private final int code;

        LogLevel(final int code) {
            this.code = code;
        }
    }
}