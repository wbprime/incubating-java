package im.wangbo.bj58.janus.schema.vertx.http;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.json.Json;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.eventbus.MessageSent;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class MessageSentCodec implements MessageCodec<MessageSent, MessageSent> {
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
        return new EventTypeMeta<MessageSent>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
