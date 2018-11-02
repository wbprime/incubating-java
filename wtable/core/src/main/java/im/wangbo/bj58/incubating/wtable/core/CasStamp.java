package im.wangbo.bj58.incubating.wtable.core;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class CasStamp {
    public abstract int cas();

    public static CasStamp of(final int cas) {
        return new AutoValue_CasStamp(cas);
    }
}
