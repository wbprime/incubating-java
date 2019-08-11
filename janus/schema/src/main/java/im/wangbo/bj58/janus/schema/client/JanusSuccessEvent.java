package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class JanusSuccessEvent implements JsonableEvent {
    public abstract TransactionId transaction();

    public abstract JsonObject data();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_SUCCESS;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .add(JanusEvents.KEY_DATA, data())
            .build();
    }

    public static Builder builder() {
        return new AutoValue_JanusSuccessEvent.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(final TransactionId transaction);

        public abstract Builder data(final JsonObject data);

        public abstract JanusSuccessEvent build();
    }
}
