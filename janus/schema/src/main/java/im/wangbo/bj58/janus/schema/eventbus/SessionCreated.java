package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SessionCreated {
    public abstract long sessionId();

    public static SessionCreated of(long sessionId) {
        return new AutoValue_SessionCreated(sessionId);
    }
}
