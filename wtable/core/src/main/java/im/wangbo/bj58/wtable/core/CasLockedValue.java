package im.wangbo.bj58.wtable.core;

import com.google.auto.value.AutoValue;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class CasLockedValue {
    public abstract Optional<Value> value();

    /**
     * Return a cas locking stamp.
     *
     * @return cas locking stamp
     */
    public abstract CasStamp casStamp();

    public static CasLockedValue create(final CasStamp casStamp) {
        return create(Optional.empty(), casStamp);
    }

    public static CasLockedValue create(final Value value, final CasStamp casStamp) {
        return create(Optional.of(value), casStamp);
    }

    private static CasLockedValue create(final Optional<Value> value, final CasStamp casStamp) {
        return new AutoValue_CasLockedValue(value, casStamp);
    }
}
