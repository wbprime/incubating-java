package im.wangbo.bj58.wtable.repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface Type2Repository<ID, V> {
    Optional<V> find(final ID id);

    void overrideInsert(final ID id, final V val);

    void delete(final ID id);

    boolean insertOnNotExists(final ID id, final V val);

    boolean updateOnExists(final ID id, final V val);

    boolean compareAndUpdate(
            final ID id, final Function<V, Optional<V>> updater
    );

    boolean compareAndDelete(
            final ID id, final Predicate<V> vWhen
    );
}
