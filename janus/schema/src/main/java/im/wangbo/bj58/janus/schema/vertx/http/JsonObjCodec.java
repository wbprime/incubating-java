package im.wangbo.bj58.janus.schema.vertx.http;

import im.wangbo.bj58.janus.schema.utils.Events;
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
class JsonObjCodec implements MessageCodec<JsonObject, JsonObject> {
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
        return Events.codecName(JsonObject.class);
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
