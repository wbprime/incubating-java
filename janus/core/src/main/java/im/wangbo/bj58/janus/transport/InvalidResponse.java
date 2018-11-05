package im.wangbo.bj58.janus.transport;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class InvalidResponse implements Response {
    public abstract String message();

    public static InvalidResponse create(String message) {
        return new AutoValue_InvalidResponse(message);
    }
}
