package im.wangbo.bj58.incubating.wtable.core;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface Value extends Supplier<byte[]> {
    /**
     * Get bytes for value.
     *
     * Do not try to changes the actual bytes returned.
     *
     * Note that changes in returned result will or will not be reflected in this {@link Value} instance.
     * It is implementation dependent.
     *
     * @return value bytes
     */
    default byte[] value() {
        return get();
    }

    default long score() {
        return 0L;
    }

    default Duration ttl() {
        return Duration.ofSeconds(0);
    }
}
