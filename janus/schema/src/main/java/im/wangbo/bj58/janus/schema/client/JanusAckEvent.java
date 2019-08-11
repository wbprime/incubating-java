package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-11, by Elvis Wang
 */
@AutoValue
abstract class JanusAckEvent implements JsonableEvent {
    abstract TransactionId transaction();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_ACK;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .build();
    }

    static JanusAckEvent of(final TransactionId transactionId) {
        return new AutoValue_JanusAckEvent(transactionId);
    }
}
