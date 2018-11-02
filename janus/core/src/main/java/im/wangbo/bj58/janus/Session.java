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
public abstract class Session {
    public abstract long id();

    public static Session of(final long id) {
        return new AutoValue_Session(id);
    }
}
