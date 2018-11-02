package im.wangbo.bj58.incubating.wtable.core;

import java.util.function.Supplier;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface ColKey extends Supplier<byte[]> {
    /**
     * Get bytes for col key.
     *
     * Do not try to changes the actual bytes returned.
     *
     * Note that changes in returned result will or will not be reflected in this {@link ColKey} instance.
     * It is implementation dependent.
     *
     * @return col key bytes
     */
    default byte[] colKey() {
        return get();
    }
}
