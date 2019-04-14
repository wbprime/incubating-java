package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.RequestMethod;
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

        public abstract OptionalLong sessionId();

        public abstract OptionalLong pluginId();

        public abstract JsonObject root();

        public static Builder builder() {
            return new AutoValue_Transport_RequestMessage.Builder().root(JsonObject.EMPTY_JSON_OBJECT);
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder request(RequestMethod request);

            public abstract Builder transaction(String transaction);

            abstract Builder sessionId(OptionalLong sessionId);
            public final Builder sessionId(long v) {
                return sessionId(OptionalLong.of(v));
            }

            abstract Builder pluginId(OptionalLong pluginId);
            public final Builder pluginId(long v) {
                return pluginId(OptionalLong.of(v));
            }

            public abstract Builder root(JsonObject root);

            public abstract RequestMessage build();
        }
    }
}
