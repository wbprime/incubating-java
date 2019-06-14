package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.GlobalArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ShowProgressStatsArg implements GlobalArg {
    @Override
    public final String argName() {
        return showsProcessStats() ? "-stats" : "-nostats";
    }

    abstract boolean showsProcessStats();

    @Override
    public final String description() {
        return "Print encoding progress/statistics or not. It is on by default.";
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    private static ShowProgressStatsArg of(boolean showsProcessStats) {
        return new AutoValue_ShowProgressStatsArg(showsProcessStats);
    }

    public static ShowProgressStatsArg on() {
        return of(true);
    }

    public static ShowProgressStatsArg off() {
        return of(false);
    }
}
