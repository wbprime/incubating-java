package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-11, by Elvis Wang
 */
@AutoValue
abstract class JanusNotifyEvent implements JsonableEvent {
    public abstract TransactionId transaction();

    public abstract PluginId pluginId();

    public abstract JsonObject data();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_NOTIFY;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .add(JanusEvents.KEY_DATA, data())
            .build();
    }

    static JanusNotifyEvent of(
        final TransactionId transaction,
        final PluginId pluginId, final JsonObject data) {
        return new AutoValue_JanusNotifyEvent(transaction, pluginId, data);
    }
}
