package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Error {
    public abstract int errorCode();

    public abstract String errorMessage();

    public static Error of(final int errorCode, final String errorMessage) {
        return new AutoValue_Error(errorCode, errorMessage);
    }
}
