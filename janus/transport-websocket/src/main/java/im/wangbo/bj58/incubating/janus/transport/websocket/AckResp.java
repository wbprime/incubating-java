package im.wangbo.bj58.incubating.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class AckResp {
    abstract String getTransactionId();

    public static AckResp fromJson(final JsonObject obj) {
        return AckResp.builder().setTransactionId(obj.getString("transaction")).build();
    }

    public static Builder builder() {
        return new AutoValue_AckResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract AckResp build();
    }
}
