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
abstract class SuccessResp {
    abstract String getTransactionId();

    abstract JsonObject getData();

    static SuccessResp fromJson(final JsonObject obj) {
        final JsonObject inner = obj.getJsonObject("data");
        return SuccessResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setData(null != inner ? inner : JsonObject.EMPTY_JSON_OBJECT)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_SuccessResp.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setData(JsonObject newData);

        public abstract SuccessResp build();
    }
}
