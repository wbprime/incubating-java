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
public abstract class DumpedItem {
    public abstract Table table();
    public abstract RowKey rowKey();
    public abstract ColKey colKey();
    public abstract Value value();

    public static Builder builder() {
        return new AutoValue_DumpedItem.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder table(Table table);

        public abstract Builder rowKey(RowKey rowKey);

        public abstract Builder colKey(ColKey colKey);

        public abstract Builder value(Value value);

        public abstract DumpedItem build();
    }
}
