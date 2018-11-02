package im.wangbo.bj58.incubating.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class JanusReq {
    abstract String requestType();

    abstract String transactionId();

    abstract OptionalLong sessionId();

    abstract OptionalLong pluginHandleId();

    abstract Optional<JsonObject> body();

    abstract Optional<PluginHandleReqJsepSub> jsep();

    // For trickle
    abstract List<PluginHandleReqCandidateSub> candidates();

    public JsonObject toJson() {
        final JsonObjectBuilder builder = Json.createObjectBuilder()
                .add("janus", requestType())
                .add("transaction", transactionId());

        sessionId().ifPresent(id -> builder.add("session_id", id));
        pluginHandleId().ifPresent(id -> builder.add("handle_id", id));
        body().ifPresent(v -> builder.add("body", v));
        jsep().ifPresent(v -> {
            final JsonObjectBuilder inner = Json.createObjectBuilder()
                    .add("type", v.getType())
                    .add("sdp", v.getSdp());

            if (null != v.getTrickle()) {
                inner.add("trickle", v.getTrickle());
            }

            builder.add("jsep", inner.build());
        });

        switch (candidates().size()) {
            case 0:
                // Do nothing
                break;
            case 1: {
                builder.add("candidate", createJsonObject(candidates().get(0)));
            } break;
            default: // >= 2
            {
                final JsonArrayBuilder inner = Json.createArrayBuilder();
                candidates().forEach(e -> inner.add(createJsonObject(e)));
                builder.add("candidates", inner.build());
            } break;
        }

        return builder.build();
    }

    private JsonObject createJsonObject(final PluginHandleReqCandidateSub sub) {
        final JsonObjectBuilder inner = Json.createObjectBuilder();

        if (Boolean.TRUE.equals(sub.getCompleted())) {
            inner.add("completed", Boolean.TRUE);
        } else {
            inner.add("sdpMid", sub.getSdpMid())
                    .add("sdpMLineIndex", sub.getSdpMlineIndex())
                    .add("candidate", sub.getCandidate());
        }
        return inner.build();
    }

    public static Builder builder() {
        return new AutoValue_JanusReq.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder requestType(String requestType);

        public abstract Builder transactionId(String transactionId);

        public abstract Builder sessionId(OptionalLong sessionId);

        public abstract Builder pluginHandleId(OptionalLong pluginHandleId);

        public abstract Builder body(Optional<JsonObject> body);

        public abstract Builder jsep(Optional<PluginHandleReqJsepSub> jsep);

        public abstract Builder candidates(List<PluginHandleReqCandidateSub> candidates);

        public final Builder sessionId(final long id) {
            return sessionId(OptionalLong.of(id));
        }

        public final Builder pluginHandleId(final long id) {
            return pluginHandleId(OptionalLong.of(id));
        }

        public final Builder body(final JsonObject body) {
            return body(Optional.ofNullable(body));
        }

        public final Builder jsep(final PluginHandleReqJsepSub jsep) {
            return jsep(Optional.ofNullable(jsep));
        }

        public abstract JanusReq build();
    }
}
