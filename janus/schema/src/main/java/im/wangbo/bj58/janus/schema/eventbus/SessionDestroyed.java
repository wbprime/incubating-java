package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SessionDestroyed {
    public abstract long sessionId();

    public static SessionDestroyed of(long sessionId) {
        return new AutoValue_SessionDestroyed(sessionId);
    }
}

