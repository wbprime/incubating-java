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
public abstract class GlobalRequest {
    public abstract RequestMethod request();

    public abstract TransactionId transaction();

    public abstract TransactionId transaction();

    public static GlobalRequest create(final RequestMethod request, final TransactionId transaction) {
        return new AutoValue_GlobalRequest(request, transaction);
    }
}
