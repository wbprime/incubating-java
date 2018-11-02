package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

import im.wangbo.bj58.janus.JanusError;
import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class JanusErrorResponse implements Response {
    public abstract Transaction transaction();
    public abstract JanusError error();

    public static Builder builder() {
        return new AutoValue_JanusErrorResponse.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder transaction(Transaction transaction);

        abstract JanusError.Builder errorBuilder();

        public final Builder errorCode(final int code) {
            errorBuilder().errorCode(code);
            return this;
        }

        public final Builder errorMessage(final String message) {
            errorBuilder().errorMessage(message);
            return this;
        }

        public abstract JanusErrorResponse build();
    }
}
