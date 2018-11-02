package im.wangbo.bj58.janus;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class JanusError {
    public abstract int errorCode();
    public abstract String errorMessage();

    public static Builder builder() {
        return new AutoValue_JanusError.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder errorCode(int errorCode);

        public abstract Builder errorMessage(String errorMessage);

        public abstract JanusError build();
    }
}
