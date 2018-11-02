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
abstract class EventResp {
    abstract String getTransactionId();

    abstract Long getPluginId();

    abstract PluginDataEntity getPluginData();

    static EventResp fromJson(final JsonObject obj) {
        final JsonObject inner = obj.getJsonObject("pluginData");
        return EventResp.builder()
                .setTransactionId(obj.getString("transaction"))
                .setPluginId(obj.getJsonNumber("sender").longValue())
                .setPluginData(EventResp.PluginDataEntity.create(inner.getString("plugin")))
                .build();
    }

    public static Builder builder() {
        return new AutoValue_EventResp.Builder();
    }

    @AutoValue
    static abstract class PluginDataEntity {
        abstract String getPluginName();

        public static PluginDataEntity create(String newPluginName) {
            return new AutoValue_EventResp_PluginDataEntity(newPluginName);
        }
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTransactionId(String newTransactionId);

        public abstract Builder setPluginId(Long newPluginId);

        public abstract Builder setPluginData(PluginDataEntity newPluginData);

        public abstract EventResp build();
    }
}
