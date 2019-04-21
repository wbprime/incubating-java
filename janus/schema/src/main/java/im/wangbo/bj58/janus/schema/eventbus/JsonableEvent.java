package im.wangbo.bj58.janus.schema.eventbus;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@FunctionalInterface
public interface JsonableEvent {
    JsonObject json();
}
