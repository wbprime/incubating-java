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
abstract class JanusErrorEvent implements JsonableEvent {
    public abstract TransactionId transaction();

    public abstract Error error();

    @Override
    public String type() {
        return JanusEvents.TYPE_JANUS_ERROR;
    }

    @Override
    public JsonObject body() {
        return Json.createObjectBuilder()
            .add(JanusEvents.KEY_TRANSACTION_ID, transaction().id())
            .add(JanusEvents.KEY_DATA,
                Json.createObjectBuilder()
                    .add(JanusEvents.KEY_DATA_ERR_CODE, error().errorCode())
                    .add(JanusEvents.KEY_DATA_ERR_MSG, error().errorMessage())
                    .build()
            )
            .build();
    }

    static JanusErrorEvent of(
        final TransactionId transaction, final Error error) {

        return new AutoValue_JanusErrorEvent(transaction, error);
    }

}
