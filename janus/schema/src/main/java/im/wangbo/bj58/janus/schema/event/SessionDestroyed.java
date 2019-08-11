package im.wangbo.bj58.janus.schema.event;

import com.google.auto.value.AutoValue;

import javax.json.Json;
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
    public final String type() {
        return MoreEvents.TYPE_SESSION_DESTROYED;
    }

    @Override
    public final JsonObject body() {
        return Json.createObjectBuilder()
            .add(MoreEvents.KEY_SESSION_ID, sessionId())
            .build();
    }

    public static SessionDestroyed of(long sessionId) {
        return new AutoValue_SessionDestroyed(sessionId);
    }
}

