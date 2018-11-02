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
public interface Type3Repository<R, C, V> {
    Optional<V> find(final R r, final C c);

    void overrideInsert(final R r, final C c, final V val);

    void delete(final R r, final C c);

    boolean insertOnNotExists(final R r, final C c, final V val);

    boolean updateOnExists(final R r, final C c, final V val);

    boolean compareAndUpdate(
            final R r, final C c, final Function<V, Optional<V>> updater
    );

    boolean compareAndDelete(
            final R r, final C c, final Predicate<V> vWhen
    );
}
