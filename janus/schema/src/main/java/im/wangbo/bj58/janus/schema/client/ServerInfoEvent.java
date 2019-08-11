package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Map;

/**
 * TODO add brief description here
 * <p>
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ServerInfoEvent implements JsonableEvent {
    public abstract TransactionId transaction();

    public abstract ServerInfo serverInfo();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_SERVERINFO;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .add(JanusEvents.KEY_DATA,
                Json.createObjectBuilder()
                    // TODO
                    .build())
            .build();
    }

    public static Builder builder() {
        return new AutoValue_ServerInfoEvent.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder transaction(TransactionId transaction);

        abstract ServerInfo.Builder serverInfoBuilder();

        public final Builder server(final ServerInfo.ServerDesc server) {
            serverInfoBuilder().server(server);
            return this;
        }

        public final Builder plugins(final Map<String, ServerInfo.PluginDesc> plugins) {
            serverInfoBuilder().plugins(plugins);
            return this;
        }

        public final Builder eventHandlers(final Map<String, ServerInfo.EvHandlerDesc> eventHandlers) {
            serverInfoBuilder().eventHandlers(eventHandlers);
            return this;

        }

        public final Builder transports(final Map<String, ServerInfo.TransportDesc> transports) {
            serverInfoBuilder().transports(transports);
            return this;
        }

        public abstract ServerInfoEvent build();
    }
}
