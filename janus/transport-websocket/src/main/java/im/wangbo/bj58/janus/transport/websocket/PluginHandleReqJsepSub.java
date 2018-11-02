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
abstract class PluginHandleReqJsepSub {
    abstract String getType();

    abstract String getSdp();

    @Nullable
    abstract Boolean getTrickle();

    public static Builder builder() {
        return new AutoValue_PluginHandleReqJsepSub.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setType(String newType);

        public abstract Builder setSdp(String newSdp);

        public abstract Builder setTrickle(Boolean newTrickle);

        public abstract PluginHandleReqJsepSub build();
    }

    static Builder offer() {
        return builder().setType("offer");
    }

    static Builder answer() {
        return builder().setType("answer");
    }
}
