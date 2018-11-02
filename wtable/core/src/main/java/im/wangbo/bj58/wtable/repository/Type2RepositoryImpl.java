package im.wangbo.bj58.wtable.repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Value;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class Type2RepositoryImpl<ID, V> implements Type2Repository<ID, V> {
    private final Repository delegate;

    private final Function<ID, RowKey> mapperToRowkey;
    private final Function<ID, ColKey> mapperToColkey;

    private final Function<V, Value> mapperToValue;
    private final Function<Value, V> mapperToEntity;

    Type2RepositoryImpl(
            final Repository delegate,
            final Function<ID, RowKey> mapperToRowkey,
            final Function<ID, ColKey> mapperToColkey,
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
    public Optional<V> find(ID id) {
        return delegate.find(mapperToRowkey.apply(id), mapperToColkey.apply(id)).map(mapperToEntity);
    }

    @Override
    public void overrideInsert(ID id, V val) {
        delegate.overrideInsert(
                mapperToRowkey.apply(id), mapperToColkey.apply(id), mapperToValue.apply(val)
        );
    }

    @Override
    public void delete(ID id) {
        delegate.delete(
                mapperToRowkey.apply(id), mapperToColkey.apply(id)
        );
    }

    @Override
    public boolean insertOnNotExists(ID id, V val) {
        return delegate.insertOnNotExists(
                mapperToRowkey.apply(id), mapperToColkey.apply(id), mapperToValue.apply(val)
        );
    }

    @Override
    public boolean updateOnExists(ID id, V val) {
        return delegate.updateOnExists(
                mapperToRowkey.apply(id), mapperToColkey.apply(id), mapperToValue.apply(val)
        );
    }

    @Override
    public boolean compareAndUpdate(ID id, Function<V, Optional<V>> updater) {
        return delegate.compareAndUpdate(
                mapperToRowkey.apply(id), mapperToColkey.apply(id),
                v -> updater.apply(mapperToEntity.apply(v)).map(mapperToValue)
        );
    }

    @Override
    public boolean compareAndDelete(ID id, Predicate<V> vWhen) {
        return delegate.compareAndDelete(
                mapperToRowkey.apply(id), mapperToColkey.apply(id),
                v -> vWhen.test(mapperToEntity.apply(v))
        );
    }
}
