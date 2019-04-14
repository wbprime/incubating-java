package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class EventBus {
    private static final Logger LOG = LoggerFactory.getLogger(EventBus.class);

    public static final String EVENT_BUS_EVENT_TYPE_KEY = "message_type";

    private final Vertx vertx;

    public EventBus(final Vertx vertx) {
        this.vertx = vertx;

        vertx.eventBus().registerCodec(new JsonObjCodec());
        vertx.eventBus().registerCodec(new JsonArrCodec());
        vertx.eventBus().registerCodec(new SessionCreatedCodec());
        vertx.eventBus().registerCodec(new SessionDestroyedCodec());
        vertx.eventBus().registerCodec(new MessageSentCodec());
        vertx.eventBus().registerCodec(new MessageReceivedCodec());
    }

    public final <T> void sendEvent(final T msg, final Class<T> clz) {
        final EventTypeMeta<?> meta = EventTypeMeta.create(clz);

        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, meta.type());
        opts.setCodecName(meta.codecName());

        LOG.info("Send event codec({}), type({}) to {}", meta.codecName(), meta.type(), meta.address());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

    @AutoValue
    public static abstract class SessionCreated {
        public abstract long sessionId();

        public static SessionCreated of(long id) {
            return new AutoValue_EventBus_SessionCreated(id);
        }
    }

    @AutoValue
    public static abstract class SessionDestroyed {
        public abstract long sessionId();

        public static SessionDestroyed of(long id) {
            return new AutoValue_EventBus_SessionDestroyed(id);
        }
    }

    @AutoValue
    public static abstract class MessageSent {
        public abstract String httpMethod();

        public abstract String fullUri();

        public abstract JsonObject message();

        public static Builder builder() {
            return new AutoValue_EventBus_MessageSent.Builder();
        }

        @AutoValue.Builder
        public abstract static class Builder {
            public abstract Builder httpMethod(String httpMethod);

            public abstract Builder fullUri(String fullUri);

            public abstract Builder message(JsonObject message);

            public abstract MessageSent build();
        }
    }

    @AutoValue
    public static abstract class MessageReceived {
        public abstract JsonObject message();

        public static MessageReceived of(JsonObject message) {
            return new AutoValue_EventBus_MessageReceived(message);
        }
    }

    private static class JsonArrCodec implements MessageCodec<JsonArray, JsonArray> {
        @Override
        public void encodeToWire(Buffer buffer, JsonArray json) {
            final String msg = json.toString();
            buffer.appendInt(msg.length());
            buffer.appendString(msg, StandardCharsets.UTF_8.name());
        }

        @Override
        public JsonArray decodeFromWire(int pos, Buffer buffer) {
            final int length = buffer.getInt(pos);
            final int beg = pos + 4;
            return Json.createReader(new StringReader(
                    buffer.getString(beg, beg + length, StandardCharsets.UTF_8.name()))
            ).readArray();
        }

        @Override
        public JsonArray transform(JsonArray json) {
            // JsonArray-s are immutable so just return it
            return json;
        }

        @Override
        public String name() {
            return new EventTypeMeta<JsonArray>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }
}
