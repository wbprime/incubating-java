package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class JanusSuccessResponse implements Response {
    public abstract Transaction transaction();
    public abstract JsonObject data();

    public static Builder builder() {
        return new AutoValue_JanusSuccessResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(Transaction transaction);

        public abstract Builder data(JsonObject data);

        public abstract JanusSuccessResponse build();
    }
}
