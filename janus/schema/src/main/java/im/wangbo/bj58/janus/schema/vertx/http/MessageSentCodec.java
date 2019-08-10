package im.wangbo.bj58.janus.schema.vertx.http;

import im.wangbo.bj58.janus.schema.event.MessageSent;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class MessageSentCodec implements MessageCodec<MessageSent, MessageSent> {
    @Override
    public void encodeToWire(Buffer buffer, MessageSent messageSent) {
        final String json = messageSent.message().toString();
        buffer.appendInt(json.length());
        buffer.appendString(json, StandardCharsets.UTF_8.name());
    }

    @Override
    public MessageSent decodeFromWire(int pos, Buffer buffer) {
        final JsonObject message;
        {
            final int len = buffer.getInt(pos);
            pos += 4;
            final String str = buffer.getString(pos, pos + len, StandardCharsets.UTF_8.name());
            message = Json.createReader(new StringReader(str)).readObject();
//                pos += len;
        }

        return MessageSent.of(message);
    }

    @Override
    public MessageSent transform(MessageSent messageSent) {
        return messageSent;
    }

    @Override
    public String name() {
        return Events.codecName(MessageSent.class);
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
