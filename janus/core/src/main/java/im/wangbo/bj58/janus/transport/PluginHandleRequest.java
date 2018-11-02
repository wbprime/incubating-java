package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

import javax.annotation.Nullable;
import javax.json.JsonObject;

import im.wangbo.bj58.janus.Candidate;
import im.wangbo.bj58.janus.Jsep;
import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.Transaction;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class PluginHandleRequest {
    public abstract String request();

    public abstract PluginHandle pluginHandle();

    public abstract Transaction transaction();

    public abstract JsonObject data();

    @Nullable
    public abstract Jsep jsep();

    public abstract ImmutableList<Candidate> candidates();

    public static Builder builder() {
        return new AutoValue_PluginHandleRequest.Builder().data(JsonObject.EMPTY_JSON_OBJECT).candidates(ImmutableList.of());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(String request);

        public abstract Builder pluginHandle(PluginHandle pluginHandle);

        public abstract Builder transaction(Transaction transaction);

        public abstract Builder data(JsonObject message);

        public abstract Builder jsep(Jsep jsep);

        public abstract Builder candidates(List<Candidate> candidates);

        public abstract PluginHandleRequest build();
    }
}
