package im.wangbo.bj58.wtable.repository;

import java.util.function.Function;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Table;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableStub;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class Repositories {
    private Repositories() { throw new AssertionError("Construction forbidden"); }

    public static Repository slaveRepository(final WtableStub wtableStub, final Table table) {
        return builder().masterSlave().wtable(wtableStub).table(table).build();
    }

    public static Repository masterRepository(final WtableStub wtableStub, final Table table) {
        return builder().masterOnly().wtable(wtableStub).table(table).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private WtableStub wtableStub;
        private Table table;
        private boolean masterOnly;

        public Builder wtable(final WtableStub wtableStub) {
            this.wtableStub = wtableStub;
            return this;
        }

        public Builder table(final Table t) {
            this.table = t;
            return this;
        }

        public Builder masterOnly() {
            this.masterOnly = true;
            return this;
        }

        public Builder masterSlave() {
            this.masterOnly = false;
            return this;
        }

        public <T> TypedBuilder1<T> entityType(final Class<T> type) {
            return new TypedBuilder1<>(build());
        }

        public Repository build() {
            checkNotNull(wtableStub, "wtableStub should not be null but was");
            checkNotNull(table, "wtableStub should not be null but was");
            return masterOnly ?
                    new MasterRepository(wtableStub, table) :
                    new SlaveRepository(wtableStub, table);
        }
    }

    public static final class TypedBuilder1<E> {
        private final Repository repository;

        private Function<E, RowKey> toRowKey;
        private Function<E, ColKey> toColKey;
        private Function<E, Value> toValue;

        private Function3<RowKey, ColKey, Value, E> toEntity;

        TypedBuilder1(Repository repository) {
            this.repository = repository;
        }

        public TypedBuilder1<E> rowKeyMapper(final Function<E, RowKey> func) {
            this.toRowKey = func;
            return this;
        }

        public TypedBuilder1<E> colKeyMapper(final Function<E, ColKey> func) {
            this.toColKey = func;
            return this;
        }

        public TypedBuilder1<E> valueMapper(final Function<E, Value> func) {
            this.toValue = func;
            return this;
        }

        public TypedBuilder1<E> entityMapper(final Function3<RowKey, ColKey, Value, E> func) {
            this.toEntity = func;
            return this;
        }

        public TypedRepository<E> build() {
            return new TypedRepositoryImpl<>(repository, toRowKey, toColKey, toValue, toEntity);
        }
    }

}
