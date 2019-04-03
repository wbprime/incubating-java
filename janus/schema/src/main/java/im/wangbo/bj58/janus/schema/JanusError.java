package im.wangbo.bj58.janus.schema;

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

    public static JanusError create(int errorCode, String errorMessage) {
        return new AutoValue_JanusError(errorCode, errorMessage);
    }
}
