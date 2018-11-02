package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class PluginHandleReqCandidateSub {
    @Nullable
    abstract String getSdpMid();
    @Nullable
    abstract Integer getSdpMlineIndex();
    @Nullable
    abstract String getCandidate();
    @Nullable
    abstract Boolean getCompleted();

    static PluginHandleReqCandidateSub completed() {
        return builder().setCompleted(true).build();
    }

    static PluginHandleReqCandidateSub candidate(
            final String sdpMid, final Integer sdpMlineIndex, final String candidate
    ) {
        return builder().setSdpMid(sdpMid).setSdpMlineIndex(sdpMlineIndex).setCandidate(candidate).build();
    }

    public static Builder builder() {
        return new AutoValue_PluginHandleReqCandidateSub.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setSdpMid(String newSdpMid);

        public abstract Builder setSdpMlineIndex(Integer newSdpMlineIndex);

        public abstract Builder setCandidate(String newCandidate);

        public abstract Builder setCompleted(Boolean newCompleted);

        public abstract PluginHandleReqCandidateSub build();
    }
}
