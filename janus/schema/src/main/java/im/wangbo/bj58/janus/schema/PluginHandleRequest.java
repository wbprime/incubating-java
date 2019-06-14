package im.wangbo.bj58.janus.schema;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.transport.PluginHandleId;
import im.wangbo.bj58.janus.schema.transport.RequestMethod;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class PluginHandleRequest {
    public abstract RequestMethod request();

    public abstract PluginHandleId pluginHandle();

    public abstract TransactionId transaction();

    public abstract Optional<JsonObject> data();

    public abstract Optional<Jsep> jsep();

    public abstract ImmutableList<Candidate> candidates();

    public static Builder builder() {
        return new AutoValue_PluginHandleRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(RequestMethod request);

        public abstract Builder pluginHandle(PluginHandleId pluginHandle);

        public abstract Builder transaction(TransactionId transaction);

        abstract Builder data(Optional<JsonObject> data);
        public final Builder data(@Nullable final JsonObject data) {
            return data(Optional.ofNullable(data));
        }

        abstract Builder jsep(Optional<Jsep> jsep);
        public final Builder jsep(@Nullable final Jsep jsep) {
            return jsep(Optional.ofNullable(jsep));
        }

        public abstract Builder candidates(ImmutableList<Candidate> candidates);

        public abstract PluginHandleRequest build();
    }
}
