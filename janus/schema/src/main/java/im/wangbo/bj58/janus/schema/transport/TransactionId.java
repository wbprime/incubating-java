package im.wangbo.bj58.janus.schema.transport;

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
public abstract class TransactionId {
    public abstract String id();

    public static TransactionId random() {
        return of(
                String.format(
                        "T%XP%xN%X",
                        System.currentTimeMillis(),
                        ThreadLocalRandom.current().nextLong(Long.MAX_VALUE),
                        Thread.currentThread().getId()
                )
        );
    }

    public static TransactionId of(final String id) {
        return new AutoValue_TransactionId(id);
    }
}
