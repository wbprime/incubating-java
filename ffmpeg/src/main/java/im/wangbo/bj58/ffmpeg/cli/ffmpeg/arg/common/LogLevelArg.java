package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.common;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class LogLevelArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-loglevel";
    }

    @Override
    public final String description() {
        return "Set logging level";
    }

    abstract LogLevel logLevel();

    @Override
    public final Optional<String> argValue() {
        return Optional.of(String.valueOf(logLevel().code));
    }

    public static LogLevelArg of(final LogLevel level) {
        return new AutoValue_LogLevelArg(level);
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
        PANIC(0), // 'panic, 0'    Only show fatal errors which could lead the process to crash, such as an assertion failure. This is not currently used for anything.
        FATAL(8), // 'fatal, 8'    Only show fatal errors. These are errors after which the process absolutely cannot continue.
        ERROR(16), // 'error, 16'   Show all errors, including ones which can be recovered from.
        WARNING(24), // 'warning, 24' Show all warnings and errors. Any message related to possibly incorrect or unexpected events will be shown.
        INFO(32), // 'info, 32'    Show informative messages during processing. This is in addition to warnings and errors. This is the default value.
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
