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
public interface Repository {
    Optional<Value> find(final RowKey r, final ColKey c);

    void overrideInsert(final RowKey r, final ColKey c, final Value val);

    void delete(final RowKey r, final ColKey c);

    boolean insertOnNotExists(final RowKey r, final ColKey c, final Value val);

    boolean updateOnExists(final RowKey r, final ColKey c, final Value val);

    boolean compareAndUpdate(
            final RowKey r, final ColKey c, final Function<Value, Optional<Value>> updater
    );

    boolean compareAndDelete(
            final RowKey r, final ColKey c, final Predicate<Value> vWhen
    );
}
