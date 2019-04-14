package im.wangbo.bj58.janus.schema.eventbus;

import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface EventBus {
    default <T> void sendEvent(final T msg, final Class<T> clz) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default <T> void registerEventHandler(final Consumer<T> handler) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
