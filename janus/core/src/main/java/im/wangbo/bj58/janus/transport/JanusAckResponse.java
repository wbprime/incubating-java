package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class JanusAckResponse implements Response {
    public abstract Transaction transaction();

    public static Builder builder() {
        return new AutoValue_JanusAckResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(Transaction transaction);

        public abstract JanusAckResponse build();
    }
}
