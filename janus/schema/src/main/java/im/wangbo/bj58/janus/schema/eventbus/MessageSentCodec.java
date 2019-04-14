package im.wangbo.bj58.janus.schema.eventbus;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonObject;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class MessageSentCodec implements MessageCodec<EventBus.MessageSent, EventBus.MessageSent> {
    @Override
    public void encodeToWire(Buffer buffer, EventBus.MessageSent messageSent) {
        buffer.appendInt(messageSent.httpMethod().length());
        buffer.appendString(messageSent.httpMethod(), StandardCharsets.UTF_8.name());
        buffer.appendInt(messageSent.fullUri().length());
        buffer.appendString(messageSent.fullUri(), StandardCharsets.UTF_8.name());

        final String json = messageSent.message().toString();
        buffer.appendInt(json.length());
        buffer.appendString(json, StandardCharsets.UTF_8.name());
    }

    @Override
    public EventBus.MessageSent decodeFromWire(int pos, Buffer buffer) {
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

        return EventBus.MessageSent.builder()
                .httpMethod(httpMethod)
                .fullUri(fullUri)
                .message(message)
                .build();
    }

    @Override
    public EventBus.MessageSent transform(EventBus.MessageSent messageSent) {
        return messageSent;
    }

    @Override
    public String name() {
        return new EventTypeMeta<EventBus.MessageSent>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
