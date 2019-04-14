package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
class HttpEventBusHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HttpEventBusHelper.class);

    static final String EVENT_BUS_EVENT_TYPE_KEY = "message_type";
    static final String EVENT_BUS_TYPE_SESSION_CREATED = "session_created";
    static final String EVENT_BUS_TYPE_SESSION_DESTROYED = "session_destroyed";
    static final String EVENT_BUS_TYPE_MESSAGE_SENT = "msg_tx";
    static final String EVENT_BUS_TYPE_MESSAGE_RECV = "msg_rx";

    private final Vertx vertx;

    HttpEventBusHelper(final Vertx vertx) {
        this.vertx = vertx;

//        vertx.eventBus().registerDefaultCodec(JsonObject.class, new JsonObjCodec());
//        vertx.eventBus().registerDefaultCodec(JsonArray.class, new JsonArrCodec());
//        vertx.eventBus().registerDefaultCodec(SessionCreated.class, new SessionCreatedCodec());
//        vertx.eventBus().registerDefaultCodec(SessionDestroyed.class, new SessionDestroyedCodec());
//        vertx.eventBus().registerDefaultCodec(MessageSent.class, new MessageSentCodec());
//        vertx.eventBus().registerDefaultCodec(MessageReceived.class, new MessageReceivedCodec());

        vertx.eventBus().registerCodec(new JsonObjCodec());
        vertx.eventBus().registerCodec(new JsonArrCodec());
        vertx.eventBus().registerCodec(new SessionCreatedCodec());
        vertx.eventBus().registerCodec(new SessionDestroyedCodec());
        vertx.eventBus().registerCodec(new MessageSentCodec());
        vertx.eventBus().registerCodec(new MessageReceivedCodec());
    }

    static abstract class EventTypeMeta<T> {
        private final Class<?> clz;

        EventTypeMeta() {
            final Type type = getClass().getGenericSuperclass();
            final ParameterizedType ptype = (ParameterizedType) type; // safe
            final Type typeArgument = ptype.getActualTypeArguments()[0]; // safe
            clz = (Class<?>) typeArgument; // safe
        }

        String address() {
            return "HttpTransport/" + clz.getName();
        }

        String codecName() {
            return clz.getName();
        }
    }

    final void sendEvent(final SessionCreated msg) {
        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, EVENT_BUS_TYPE_SESSION_CREATED);

        final EventTypeMeta<?> meta = new EventTypeMeta<SessionCreated>() {
        };
        opts.setCodecName(meta.codecName());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

    final void sendEvent(final SessionDestroyed msg) {
        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, EVENT_BUS_TYPE_SESSION_DESTROYED);

        final EventTypeMeta<?> meta = new EventTypeMeta<SessionDestroyed>() {
        };
        opts.setCodecName(meta.codecName());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

    final void sendEvent(final MessageSent msg) {
        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, EVENT_BUS_TYPE_MESSAGE_SENT);

        final EventTypeMeta<?> meta = new EventTypeMeta<MessageSent>() {
        };
        opts.setCodecName(meta.codecName());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

    final void sendEvent(final MessageReceived msg) {
        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, EVENT_BUS_TYPE_MESSAGE_RECV);

        final EventTypeMeta<?> meta = new EventTypeMeta<MessageReceived>() {
        };
        opts.setCodecName(meta.codecName());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

    @AutoValue
    static abstract class SessionCreated {
        abstract long sessionId();

        static SessionCreated of(long id) {
            return new AutoValue_HttpEventBusHelper_SessionCreated(id);
        }
    }

    @AutoValue
    static abstract class SessionDestroyed {
        abstract long sessionId();

        static SessionDestroyed of(long id) {
            return new AutoValue_HttpEventBusHelper_SessionDestroyed(id);
        }
    }

    @AutoValue
    static abstract class MessageSent {
        abstract String httpMethod();

        abstract String fullUri();

        abstract JsonObject message();

        static Builder builder() {
            return new AutoValue_HttpEventBusHelper_MessageSent.Builder();
        }

        @AutoValue.Builder
        abstract static class Builder {
            abstract Builder httpMethod(String httpMethod);

            abstract Builder fullUri(String fullUri);

            abstract Builder message(JsonObject message);

            abstract MessageSent build();
        }
    }

    @AutoValue
    static abstract class MessageReceived {
        abstract JsonObject message();

        public static MessageReceived create(JsonObject message) {
            return new AutoValue_HttpEventBusHelper_MessageReceived(message);
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

    private static class JsonObjCodec implements MessageCodec<JsonObject, JsonObject> {
        @Override
        public void encodeToWire(Buffer buffer, JsonObject json) {
            final String msg = json.toString();
            buffer.appendInt(msg.length());
            buffer.appendString(msg, StandardCharsets.UTF_8.name());
        }

        @Override
        public JsonObject decodeFromWire(int pos, Buffer buffer) {
            final int length = buffer.getInt(pos);
            final int beg = pos + 4;
            return Json.createReader(new StringReader(
                    buffer.getString(beg, beg + length, StandardCharsets.UTF_8.name()))
            ).readObject();
        }

        @Override
        public JsonObject transform(JsonObject json) {
            // JsonObject-s are immutable so just return it
            return json;
        }

        @Override
        public String name() {
            return new EventTypeMeta<JsonObject>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }

    private static class SessionDestroyedCodec implements MessageCodec<SessionDestroyed, SessionDestroyed> {
        @Override
        public void encodeToWire(Buffer buffer, SessionDestroyed sessionDestroyed) {
            buffer.appendLong(sessionDestroyed.sessionId());
        }

        @Override
        public SessionDestroyed decodeFromWire(int pos, Buffer buffer) {
            return SessionDestroyed.of(buffer.getLong(pos));
        }

        @Override
        public SessionDestroyed transform(SessionDestroyed sessionDestroyed) {
            return sessionDestroyed;
        }

        @Override
        public String name() {
            return new EventTypeMeta<SessionDestroyed>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }

    private static class SessionCreatedCodec implements MessageCodec<SessionCreated, SessionCreated> {
        @Override
        public void encodeToWire(Buffer buffer, SessionCreated sessionCreated) {
            buffer.appendLong(sessionCreated.sessionId());
        }

        @Override
        public SessionCreated decodeFromWire(int pos, Buffer buffer) {
            return SessionCreated.of(buffer.getLong(pos));
        }

        @Override
        public SessionCreated transform(SessionCreated sessionCreated) {
            return sessionCreated;
        }

        @Override
        public String name() {
            return new EventTypeMeta<SessionCreated>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }

    private static class MessageSentCodec implements MessageCodec<MessageSent, MessageSent> {
        @Override
        public void encodeToWire(Buffer buffer, MessageSent messageSent) {
            buffer.appendInt(messageSent.httpMethod().length());
            buffer.appendString(messageSent.httpMethod(), StandardCharsets.UTF_8.name());
            buffer.appendInt(messageSent.fullUri().length());
            buffer.appendString(messageSent.fullUri(), StandardCharsets.UTF_8.name());

            final String json = messageSent.message().toString();
            buffer.appendInt(json.length());
            buffer.appendString(json, StandardCharsets.UTF_8.name());
        }

        @Override
        public MessageSent decodeFromWire(int pos, Buffer buffer) {
            final String httpMethod;
            {
                final int len = buffer.getInt(pos);
                pos += 4;
                httpMethod = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
                pos += len;
            }

            final String fullUri;
            {
                final int len = buffer.getInt(pos);
                pos += 4;
                fullUri = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
                pos += len;
            }

            final JsonObject message;
            {
                final int len = buffer.getInt(pos);
                pos += 4;
                final String str = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
                message = Json.createReader(new StringReader(str)).readObject();
//                pos += len;
            }

            return MessageSent.builder()
                    .httpMethod(httpMethod)
                    .fullUri(fullUri)
                    .message(message)
                    .build();
        }

        @Override
        public MessageSent transform(MessageSent messageSent) {
            return messageSent;
        }

        @Override
        public String name() {
            return new EventTypeMeta<MessageSent>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }

    private static class MessageReceivedCodec implements MessageCodec<MessageReceived, MessageReceived> {
        @Override
        public void encodeToWire(Buffer buffer, MessageReceived msg) {
            final String json = msg.message().toString();
            buffer.appendInt(json.length());
            buffer.appendString(json, StandardCharsets.UTF_8.name());
        }

        @Override
        public MessageReceived decodeFromWire(int pos, Buffer buffer) {
            final JsonObject message;
            {
                final int len = buffer.getInt(pos);
                pos += 4;
                final String str = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
                message = Json.createReader(new StringReader(str)).readObject();
//                pos += len;
            }

            return MessageReceived.create(message);
        }

        @Override
        public MessageReceived transform(MessageReceived message) {
            return message;
        }

        @Override
        public String name() {
            return new EventTypeMeta<MessageReceived>() {}.codecName();
        }

        @Override
        public byte systemCodecID() {
            return -1;
        }
    }
}
