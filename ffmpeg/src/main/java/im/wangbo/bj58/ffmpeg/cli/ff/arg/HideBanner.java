package im.wangbo.bj58.ffmpeg.cli.ff.arg;

import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.common.EmptyValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class HideBanner extends ArgSpec<EmptyValue> {
    @Override
    public final String name() {
        return "-hide_banner";
    }

    @Override
    public final String description() {
        return "Suppress printing banner";
    }

    public static HideBanner instance() {
        return new AutoValue_HideBanner();
    }
}
