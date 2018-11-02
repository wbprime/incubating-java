package im.wangbo.bj58.incubating.wtable.repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import im.wangbo.bj58.incubating.wtable.core.ColKey;
import im.wangbo.bj58.incubating.wtable.core.RowKey;
import im.wangbo.bj58.incubating.wtable.core.Value;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class Type3RepositoryImpl<R, C, V> implements Type3Repository<R, C, V> {
    private final Repository delegate;

    private final Function<R, RowKey> mapperToRowkey;
    private final Function<C, ColKey> mapperToColkey;

    private final Function<V, Value> mapperToValue;
    private final Function<Value, V> mapperToEntity;

    Type3RepositoryImpl(
            final Repository delegate,
            final Function<R, RowKey> mapperToRowkey,
            final Function<C, ColKey> mapperToColkey,
            final Function<V, Value> mapperToValue,
            final Function<Value, V> mapperToEntity
    ) {
        this.delegate = delegate;
        this.mapperToRowkey = mapperToRowkey;
        this.mapperToColkey = mapperToColkey;
        this.mapperToValue = mapperToValue;
        this.mapperToEntity = mapperToEntity;
    }

    @Override
    public Optional<V> find(R r, C c) {
        return delegate.find(mapperToRowkey.apply(r), mapperToColkey.apply(c)).map(mapperToEntity);
    }

    @Override
    public void overrideInsert(R r, C c, V val) {
        delegate.overrideInsert(
                mapperToRowkey.apply(r), mapperToColkey.apply(c), mapperToValue.apply(val)
        );
    }

    @Override
    public void delete(R r, C c) {
        delegate.delete(
                mapperToRowkey.apply(r), mapperToColkey.apply(c)
        );
    }

    @Override
    public boolean insertOnNotExists(R r, C c, V val) {
        return delegate.insertOnNotExists(
                mapperToRowkey.apply(r), mapperToColkey.apply(c), mapperToValue.apply(val)
        );
    }

    @Override
    public boolean updateOnExists(R r, C c, V val) {
        return delegate.updateOnExists(
                mapperToRowkey.apply(r), mapperToColkey.apply(c), mapperToValue.apply(val)
        );
    }

    @Override
    public boolean compareAndUpdate(R r, C c, Function<V, Optional<V>> updater) {
        return delegate.compareAndUpdate(
                mapperToRowkey.apply(r), mapperToColkey.apply(c),
                v -> updater.apply(mapperToEntity.apply(v)).map(mapperToValue)
        );
    }

    @Override
    public boolean compareAndDelete(R r, C c, Predicate<V> vWhen) {
        return delegate.compareAndDelete(
                mapperToRowkey.apply(r), mapperToColkey.apply(c),
                v -> vWhen.test(mapperToEntity.apply(v))
        );
    }
}
