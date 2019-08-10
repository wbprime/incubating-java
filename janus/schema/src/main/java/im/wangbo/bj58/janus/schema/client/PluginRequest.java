package im.wangbo.bj58.janus.schema.client;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.TransactionId;

import javax.annotation.Nullable;
import javax.json.JsonObject;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class PluginRequest {
    public abstract Request.Type request();

    public abstract PluginId pluginHandle();

    public abstract TransactionId transaction();

    public abstract Optional<JsonObject> data();

    public abstract Optional<Jsep> jsep();

    public abstract ImmutableList<Candidate> candidates();

    public static Builder builder() {
        return new AutoValue_PluginRequest.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder request(Request.Type request);

        public abstract Builder pluginHandle(PluginId pluginHandle);

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

        public abstract PluginRequest build();
    }
}
