package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class SessionRequest {
    public abstract String request();

    public abstract Session session();

    public abstract Transaction transaction();

    public abstract JsonObject message();

    public static Builder builder() {
        return new AutoValue_SessionRequest.Builder().message(JsonObject.EMPTY_JSON_OBJECT);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(String request);

        public abstract Builder session(Session session);

        public abstract Builder transaction(Transaction transaction);

        public abstract Builder message(JsonObject message);

        public abstract SessionRequest build();
    }
}
