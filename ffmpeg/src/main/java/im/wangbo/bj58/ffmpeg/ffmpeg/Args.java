package im.wangbo.bj58.ffmpeg.ffmpeg;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.Value;
import im.wangbo.bj58.ffmpeg.filter.FilterGraph;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class Args {
    private Args() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static Arg showLicense() {
        return Arg.named("-L");
    }

    public static Arg showVersion() {
        return Arg.named("-version");
    }

    public static enum LogLevel implements Value {
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

        @Override
        public final String asString() {
            return String.valueOf(code);
        }
    }

    public static Arg logLevel(final LogLevel level) {
        return Arg.paired("-loglevel", level);
    }

    public static Arg hideBanner() {
        return Arg.named("-hide_banner");
    }

    public static Arg forceOverwrite() {
        return Arg.named("-y");
    }

    public static Arg neverOverwrite() {
        return Arg.named("-n");
    }

    public static Arg inputUri(final String uri) {
        return Arg.paired("-i", uri);
    }

    public static Arg outputUri(final String uri) {
        return Arg.named(uri);
    }

    public static Arg complexFilterGraph(final FilterGraph globalFilterGraph) {
        return Arg.paired("-filter_complex", globalFilterGraph.asString());
    }

}
