package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class MessageReceived {
    public abstract JsonObject message();

    public static MessageReceived of(JsonObject message) {
        return new AutoValue_MessageReceived(message);
    }

}
