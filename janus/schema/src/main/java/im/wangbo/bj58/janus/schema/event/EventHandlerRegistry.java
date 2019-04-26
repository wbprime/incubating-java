package im.wangbo.bj58.janus.schema.event;

import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface EventHandlerRegistry {
    default <T extends JsonableEvent> void handler(final Consumer<T> handler, final Class<T> messageType) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default void exceptionHandler(final Consumer<Exception> handler) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
