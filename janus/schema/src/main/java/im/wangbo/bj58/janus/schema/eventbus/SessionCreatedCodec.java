package im.wangbo.bj58.janus.schema.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class SessionCreatedCodec implements MessageCodec<EventBus.SessionCreated, EventBus.SessionCreated> {
    @Override
    public void encodeToWire(Buffer buffer, EventBus.SessionCreated sessionCreated) {
        buffer.appendLong(sessionCreated.sessionId());
    }

    @Override
    public EventBus.SessionCreated decodeFromWire(int pos, Buffer buffer) {
        return EventBus.SessionCreated.of(buffer.getLong(pos));
    }

    @Override
    public EventBus.SessionCreated transform(EventBus.SessionCreated sessionCreated) {
        return sessionCreated;
    }

    @Override
    public String name() {
        return new EventTypeMeta<EventBus.SessionCreated>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
