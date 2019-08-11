package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 * <p>
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class WebrtcDownEvent implements JsonableEvent {
    public abstract TransactionId transaction();

    public abstract SessionId sessionId();

    public abstract PluginId pluginId();

    public abstract String message();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_WEBRTC_DOWN;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .add(JanusEvents.KEY_PLUGINHANDLE_ID, pluginId().id())
            .add(JanusEvents.KEY_DATA,
                Json.createObjectBuilder()
                    .add(JanusEvents.KEY_DATA_MESSAGE, message())
                    .build())
            .build();
    }

    public static Builder builder() {
        return new AutoValue_WebrtcDownEvent.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(final TransactionId transaction);

        public abstract Builder sessionId(final SessionId sessionId);

        public abstract Builder pluginId(final PluginId pluginId);

        public abstract Builder message(String str);

        public abstract WebrtcDownEvent build();
    }
}
