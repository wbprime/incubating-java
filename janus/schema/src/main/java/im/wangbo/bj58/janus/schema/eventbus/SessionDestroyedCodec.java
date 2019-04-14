package im.wangbo.bj58.janus.schema.eventbus;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class SessionDestroyedCodec implements MessageCodec<EventBus.SessionDestroyed, EventBus.SessionDestroyed> {
    @Override
    public void encodeToWire(Buffer buffer, EventBus.SessionDestroyed sessionDestroyed) {
        buffer.appendLong(sessionDestroyed.sessionId());
    }

    @Override
    public EventBus.SessionDestroyed decodeFromWire(int pos, Buffer buffer) {
        return EventBus.SessionDestroyed.of(buffer.getLong(pos));
    }

    @Override
    public EventBus.SessionDestroyed transform(EventBus.SessionDestroyed sessionDestroyed) {
        return sessionDestroyed;
    }

    @Override
    public String name() {
        return new EventTypeMeta<EventBus.SessionDestroyed>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
