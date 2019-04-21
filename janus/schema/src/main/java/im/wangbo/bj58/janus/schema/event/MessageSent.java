package im.wangbo.bj58.janus.schema.event;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MessageSent implements JsonableEvent {
    public abstract JsonObject message();

    @Override
    public final String eventType() {
        return MoreEvents.TYPE_MESSAGE_SENT;
    }

    @Override
    public final JsonObject eventBody() {
        return message();
    }

    public static MessageSent of(final JsonObject message) {
        return new AutoValue_MessageSent(message);
    }
}
