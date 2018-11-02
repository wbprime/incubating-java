package im.wangbo.bj58.janus.transport;

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
public abstract class UnknownResponse implements Response {
    public abstract JsonObject message();

    public static UnknownResponse create(JsonObject msg) {
        return new AutoValue_UnknownResponse(msg);
    }
}
