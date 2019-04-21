package im.wangbo.bj58.janus.schema.event;

import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface EventHandlerRegistry {
    default void register(final Consumer<? extends JsonableEvent> handler) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    default void deregister(final Consumer<? extends JsonableEvent> handler) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
