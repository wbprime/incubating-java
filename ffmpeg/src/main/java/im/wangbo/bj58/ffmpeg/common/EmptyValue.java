package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class EmptyValue implements Value {

    @Override
    public final String asString() {
        throw new UnsupportedOperationException("unsupported yet"):
    }

    @Override
    public final Optional<String> stringify() {
        return Optional.empty();
    }

    public static EmptyValue of() {
        return new AutoValue_EmptyValue();
    }
}
