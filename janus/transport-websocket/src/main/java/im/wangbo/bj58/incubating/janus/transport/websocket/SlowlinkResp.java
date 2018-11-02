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
abstract class SlowlinkResp {
    abstract String getTransactionId();
    abstract Long getSessionId();
    abstract Long getPluginId();
    abstract Integer getNumberOfNacks();
    abstract Boolean getUplink();

    static SlowlinkResp fromJson(final JsonObject obj) {
        return SlowlinkResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setSessionId(obj.getJsonNumber("session_id").longValue())
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .setNumberOfNacks(obj.getInt("nacks"))
                .setUplink(obj.getBoolean("uplink"))
                .build();
    }

    public static Builder builder() {
        return new AutoValue_SlowlinkResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setSessionId(Long newSessionId);

        public abstract Builder setPluginId(Long newPluginId);

        public abstract Builder setNumberOfNacks(Integer newNumberOfNacks);

        public abstract Builder setUplink(Boolean newUplink);

        public abstract SlowlinkResp build();
    }
}
