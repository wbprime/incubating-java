package im.wangbo.bj58.ffmpeg.cli.exec;

import com.google.auto.value.AutoValue;

import java.util.OptionalLong;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-20, by Elvis Wang
 */
@AutoValue
abstract class NoTimeouting implements CliProcessTimeoutingStrategy {
    @Override
    public OptionalLong millis() {
        return OptionalLong.empty();
    }

    static NoTimeouting of() {
        return new AutoValue_NoTimeouting();
    }
}
