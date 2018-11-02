package im.wangbo.bj58.incubating.wtable.repository;

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
public interface Type1Repository<E> {
    Optional<E> find(final E id);

    void overrideInsert(final E e);

    void delete(final E id);

    boolean insertOnNotExists(final E e);

    boolean updateOnExists(final E e);

    boolean compareAndUpdate(
            final E id, final Function<E, Optional<E>> updater
    );

    boolean compareAndDelete(
            final E id, final Predicate<E> vWhen
    );
}
