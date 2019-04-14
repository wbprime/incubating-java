package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.PluginHandleId;
import im.wangbo.bj58.janus.schema.RequestMethod;
import im.wangbo.bj58.janus.schema.SessionId;
import im.wangbo.bj58.janus.schema.TransactionId;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Transport {
    CompletableFuture<Void> connect(final URI uri);

    CompletableFuture<Void> close();

    CompletableFuture<Void> send(final RequestMessage msg);

    Transport handler(final Consumer<JsonObject> handler);

    Transport exceptionHandler(final Consumer<Throwable> handler);

    static Transport noop() {
        return NoopTransport.instance();
    }

    static Transport websocket() {
        return new WebSocketTransport();
    }

    @AutoValue
    abstract class RequestMessage {
        public abstract RequestMethod request();

        public abstract TransactionId transaction();

        public abstract Optional<SessionId> sessionId();

        public abstract Optional<PluginHandleId> pluginId();

        public abstract JsonObject root();

        public static Builder builder() {
            return new AutoValue_Transport_RequestMessage.Builder().root(JsonObject.EMPTY_JSON_OBJECT);
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

            public abstract RequestMessage build();
        }
    }
}
