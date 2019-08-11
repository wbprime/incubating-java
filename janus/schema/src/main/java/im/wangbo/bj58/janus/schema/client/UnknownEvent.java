package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 * <p>
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class UnknownEvent implements JsonableEvent {
    public abstract JsonObject message();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_UNRECOGNIZED;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_DATA, message())
            .build();
    }

    public static UnknownEvent of(final JsonObject msg) {
        return new AutoValue_UnknownEvent(msg);
    }
}
