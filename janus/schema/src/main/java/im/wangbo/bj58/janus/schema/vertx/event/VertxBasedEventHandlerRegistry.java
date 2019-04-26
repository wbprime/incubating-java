package im.wangbo.bj58.janus.schema.vertx.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Consumer;

import im.wangbo.bj58.janus.schema.event.EventHandlerRegistry;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class VertxBasedEventHandlerRegistry implements EventHandlerRegistry {
    private final Multimap<String, Consumer<? extends JsonableEvent>> mappedHandlers;
    private final Set<Consumer<Exception>> exHandlers;

    private VertxBasedEventHandlerRegistry(final Vertx vertx) {
        this.mappedHandlers = HashMultimap.create();
        this.exHandlers = Sets.newConcurrentHashSet();

        vertx.eventBus().consumer(
                "",
                (Handler<Message<JsonableEvent>>) event -> {
                    for (final Consumer<JsonableEvent> handler : handlers) {
                        try {
                            handler.accept(event.body());
                        } catch (Exception ex) {
                            for (Consumer<Exception> exHandler : exHandlers) {
                                exHandler.accept(ex);
                            }
                        }
                    }
                }
        );

    }

    public static VertxBasedEventHandlerRegistry create(final Vertx vertx) {
        return new VertxBasedEventHandlerRegistry(vertx);
    }

    @Override
    public synchronized <T extends JsonableEvent> void handler(Consumer<T> handler, Class<T> messageType) {
        mappedHandlers.put(messageType.getName(), handler);
    }

    @Override
    public void exceptionHandler(final Consumer<Exception> handler) {
        exHandlers.add(handler);
    }
}
