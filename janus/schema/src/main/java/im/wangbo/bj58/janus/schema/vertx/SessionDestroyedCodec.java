package im.wangbo.bj58.janus.schema.vertx;

import im.wangbo.bj58.janus.schema.eventbus.SessionDestroyed;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class SessionDestroyedCodec implements MessageCodec<SessionDestroyed, SessionDestroyed> {
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
        return new EventTypeMeta<SessionDestroyed>() {
        }.codecName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
