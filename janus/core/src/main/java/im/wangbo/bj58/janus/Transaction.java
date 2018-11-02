package im.wangbo.bj58.janus;

import com.google.auto.value.AutoValue;

import java.util.concurrent.ThreadLocalRandom;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class Transaction {
    public abstract String id();

    public static Transaction of() {
        return of(
                String.format(
                        "T%XP%xN%X",
                        System.currentTimeMillis(),
                        ThreadLocalRandom.current().nextLong(Long.MAX_VALUE),
                        Thread.currentThread().getId()
                )
        );
    }

    public static Transaction of(final String id) {
        return new AutoValue_Transaction(id);
    }
}
