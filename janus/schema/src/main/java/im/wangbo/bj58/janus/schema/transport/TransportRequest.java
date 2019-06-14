package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class TransportRequest {
    public abstract RequestMethod request();

    public abstract TransactionId transaction();

    public abstract Optional<SessionId> sessionId();

    public abstract Optional<PluginHandleId> pluginId();

    public abstract JsonObject root();

    public static Builder builder() {
        return new AutoValue_TransportRequest.Builder().root(JsonObject.EMPTY_JSON_OBJECT);
    }

    public static Builder serverInfoMessageBuilder() {
        return builder().request(RequestMethod.serverInfo());
    }

    public static Builder createSessionMessageBuilder() {
        return builder().request(RequestMethod.createSession());
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder request(RequestMethod request);

        public abstract Builder transaction(final TransactionId transaction);

        abstract Builder sessionId(Optional<SessionId> sessionId);

        public final Builder sessionId(@Nullable SessionId sessionId) {
            return sessionId(Optional.ofNullable(sessionId));
        }

        abstract Builder pluginId(Optional<PluginHandleId> pluginId);

        public final Builder pluginId(@Nullable PluginHandleId v) {
            return pluginId(Optional.ofNullable(v));
        }

        public abstract Builder root(JsonObject root);

        public abstract TransportRequest build();
    }
}
