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
public abstract class ScannedItem {
    public abstract ColKey colKey();
    public abstract Value value();

    public static Builder builder() {
        return new AutoValue_ScannedItem.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder colKey(ColKey colKey);

        public abstract Builder value(Value value);

        public abstract ScannedItem build();
    }
}
