package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.auto.value.AutoValue;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
@AutoValue
abstract class AlwaysInvalid implements ValueValidator {
    static AlwaysInvalid of() {
        return new AutoValue_AlwaysInvalid();
    }

    @Override
    public boolean validate(String str) {
        return false;
    }
}
