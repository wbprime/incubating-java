package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.annotation.Nullable;
import javax.json.JsonObject;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SessionRequest {
    public abstract Request.Type request();

    public abstract SessionId session();

    public abstract TransactionId transaction();

    public abstract Optional<JsonObject> message();

    public static Builder builder() {
        return new AutoValue_SessionRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(Request.Type request);

        public abstract Builder session(SessionId session);

        public abstract Builder transaction(TransactionId transaction);

        abstract Builder message(Optional<JsonObject> message);

        public final Builder message(@Nullable final JsonObject message) {
            return message(Optional.ofNullable(message));
        }

        public abstract SessionRequest build();
    }
}
