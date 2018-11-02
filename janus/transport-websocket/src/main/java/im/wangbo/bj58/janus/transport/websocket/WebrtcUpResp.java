package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbProperty;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class WebrtcUpResp {
    @JsonbProperty("transaction")
    private String transactionId;
    @JsonbProperty("session_id")
    private Long sessionId;
    @JsonbProperty("sender")
    private Long pluginId;

    abstract String getTransactionId();
    abstract Long getSessionId();
    abstract Long getPluginId();

    static WebrtcUpResp fromJson(final JsonObject obj) {
        return WebrtcUpResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setSessionId(obj.getJsonNumber("session_id").longValue())
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .build();
    }

    public static Builder builder() {
        return new AutoValue_WebrtcUpResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setSessionId(Long newSessionId);

        public abstract Builder setPluginId(Long newPluginId);

        public abstract WebrtcUpResp build();
    }
}
