package im.wangbo.bj58.janus.schema.event;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface JsonableEvent {
    String eventType();

    JsonObject eventBody();
}
