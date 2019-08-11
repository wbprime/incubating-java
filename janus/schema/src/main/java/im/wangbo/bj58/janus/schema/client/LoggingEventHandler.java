package im.wangbo.bj58.janus.schema.client;

import im.wangbo.bj58.janus.schema.event.JsonableEvent;

import java.util.StringJoiner;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-11, by Elvis Wang
 */
class LoggingEventHandler implements EventHandler {
    @Override
    public void accept(final JsonableEvent event) {

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LoggingEventHandler.class.getSimpleName() + "[", "]")
            .toString();
    }
}
