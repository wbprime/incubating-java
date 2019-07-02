package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class AllStreamSpecifier implements StreamSpecifier {
    @Override
    public final String asString() {
        return "";
    }

    static AllStreamSpecifier of() {
        return new AutoValue_AllStreamSpecifier();
    }
}
