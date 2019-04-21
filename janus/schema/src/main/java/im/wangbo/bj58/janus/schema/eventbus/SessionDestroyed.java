package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SessionDestroyed implements JsonableEvent {
    public abstract long sessionId();

    @Override
    public JsonObject json() {
        return JsonObject.EMPTY_JSON_OBJECT;
    }

    public static SessionDestroyed of(long sessionId) {
        return new AutoValue_SessionDestroyed(sessionId);
    }
}

