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
public abstract class RequestMethod {
    public abstract String type();

    public static RequestMethod of(final String type) {
        return new AutoValue_RequestMethod(type);
    }

}
