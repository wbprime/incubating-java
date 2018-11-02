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
final class Type1RepositoryImpl<E> implements Type1Repository<E> {
    private final Repository delegate;

    private final Function<E, RowKey> mapperToRowkey;
    private final Function<E, ColKey> mapperToColkey;
    private final Function<E, Value> mapperToValue;

    private final Function3<RowKey, ColKey, Value, E> mapperToEntity;

    Type1RepositoryImpl(
            final Repository delegate,
            final Function<E, RowKey> mapperToRowkey,
            final Function<E, ColKey> mapperToColkey,
            final Function<E, Value> mapperToValue,
            final Function3<RowKey, ColKey, Value, E> mapperToEntity
    ) {
        this.delegate = delegate;
        this.mapperToRowkey = mapperToRowkey;
        this.mapperToColkey = mapperToColkey;
        this.mapperToValue = mapperToValue;
        this.mapperToEntity = mapperToEntity;
    }

    @Override
    public Optional<E> find(E id) {
        final RowKey rowKey = mapperToRowkey.apply(id);
        final ColKey colKey = mapperToColkey.apply(id);
        final Optional<Value> value = delegate.find(rowKey, colKey);

        return value.map(v -> mapperToEntity.apply(rowKey, colKey, v));
    }

    @Override
    public void overrideInsert(E e) {
        delegate.overrideInsert(
                mapperToRowkey.apply(e), mapperToColkey.apply(e), mapperToValue.apply(e)
        );
    }

    @Override
    public void delete(E id) {
        delegate.delete(
                mapperToRowkey.apply(id), mapperToColkey.apply(id)
        );
    }

    @Override
    public boolean insertOnNotExists(E e) {
        return delegate.insertOnNotExists(
                mapperToRowkey.apply(e), mapperToColkey.apply(e), mapperToValue.apply(e)
        );
    }

    @Override
    public boolean updateOnExists(E e) {
        return delegate.updateOnExists(
                mapperToRowkey.apply(e), mapperToColkey.apply(e), mapperToValue.apply(e)
        );
    }

    @Override
    public boolean compareAndUpdate(E id, Function<E, Optional<E>> updater) {
        final RowKey rowKey = mapperToRowkey.apply(id);
        final ColKey colKey = mapperToColkey.apply(id);
        return delegate.compareAndUpdate(
                rowKey, colKey,
                v -> updater.apply(mapperToEntity.apply(rowKey, colKey, v)).map(mapperToValue)
        );
    }

    @Override
    public boolean compareAndDelete(E id, Predicate<E> vWhen) {
        final RowKey rowKey = mapperToRowkey.apply(id);
        final ColKey colKey = mapperToColkey.apply(id);
        return delegate.compareAndDelete(
                rowKey, colKey,
                v -> vWhen.test(mapperToEntity.apply(rowKey, colKey, v))
        );
    }
}
