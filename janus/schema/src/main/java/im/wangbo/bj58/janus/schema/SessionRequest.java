package im.wangbo.bj58.janus.schema;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class SessionRequest {
    public abstract RequestMethod request();

    public abstract SessionId session();

    public abstract TransactionId transaction();

    public abstract Optional<JsonObject> message();

    public static Builder builder() {
        return new AutoValue_SessionRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(RequestMethod request);

        public abstract Builder session(SessionId session);

        public abstract Builder transaction(TransactionId transaction);

        abstract Builder message(Optional<JsonObject> message);
        public final Builder message(@Nullable final JsonObject message) {
            return message(Optional.ofNullable(message));
        }

        public abstract SessionRequest build();
    }
}
