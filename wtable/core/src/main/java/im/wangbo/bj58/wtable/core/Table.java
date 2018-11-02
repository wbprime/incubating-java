package im.wangbo.bj58.wtable.core;

import com.google.auto.value.AutoValue;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class Table {
    public abstract int id();

    public static Table of(int tableId) {
        checkArgument(tableId >= 0, "tableId should be ge 0 but was %s", tableId);
        return new AutoValue_Table(tableId);
    }
}
