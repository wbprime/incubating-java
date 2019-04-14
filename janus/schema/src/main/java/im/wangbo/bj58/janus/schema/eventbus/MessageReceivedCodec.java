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
class MessageReceivedCodec implements MessageCodec<EventBus.MessageReceived, EventBus.MessageReceived> {
    @Override
    public void encodeToWire(Buffer buffer, EventBus.MessageReceived msg) {
        final String json = msg.message().toString();
        buffer.appendInt(json.length());
        buffer.appendString(json, StandardCharsets.UTF_8.name());
    }

    @Override
    public EventBus.MessageReceived decodeFromWire(int pos, Buffer buffer) {
        final JsonObject message;
        {
            final int len = buffer.getInt(pos);
            pos += 4;
            final String str = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
            message = Json.createReader(new StringReader(str)).readObject();
//                pos += len;
        }

        return EventBus.MessageReceived.of(message);
    }

    @Override
    public EventBus.MessageReceived transform(EventBus.MessageReceived message) {
        return message;
    }

    @Override
    public String name() {
        return new EventTypeMeta<EventBus.MessageReceived>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
