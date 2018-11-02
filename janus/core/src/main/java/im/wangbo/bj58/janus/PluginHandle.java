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
public abstract class PluginHandle {
    public abstract Session session();
    public abstract long id();

    public static PluginHandle of(final Session session, final long id) {
        return new AutoValue_PluginHandle(session, id);
    }
}
