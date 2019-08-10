package im.wangbo.bj58.janus.schema.event.handler;

import im.wangbo.bj58.janus.schema.event.JsonableEvent;

import java.util.function.Consumer;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
public interface EventHandler extends Consumer<JsonableEvent> {
    @Override
    void accept(final JsonableEvent event);
}
