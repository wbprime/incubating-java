package im.wangbo.bj58.janus.schema.client;

import com.google.common.collect.Lists;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;

import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Consumer;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
public interface EventHandler extends Consumer<JsonableEvent> {
    static Iterable<EventHandler> builtinEventHandlers() {
        final List<EventHandler> list = Lists.newArrayList();

        list.add(new LoggingEventHandler());

        return list;
    }

    static Iterable<EventHandler> spiBasedEventHandlers() {
        return ServiceLoader.load(EventHandler.class);
    }

    @Override
    void accept(final JsonableEvent event);
}
