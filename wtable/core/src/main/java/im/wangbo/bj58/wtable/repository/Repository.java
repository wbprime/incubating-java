package im.wangbo.bj58.wtable.repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableCheckedException;

/**
 * Wrap general purpose {@link im.wangbo.bj58.wtable.core.WtableStub} operations.
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface Repository {
    /**
     * Find value by row key and col key.
     *
     * @param r row key
     * @param c col key
     * @return value if found, otherwise {@link Optional#empty()}
     * @throws WtableCheckedException if failed
     */
    Optional<Value> find(final RowKey r, final ColKey c) throws WtableCheckedException;

    /**
     * Set a value by row key and col key, override if exists.
     *
     * @param r row key
     * @param c col key
     * @param val value
     * @throws WtableCheckedException if failed
     */
    void overrideInsert(final RowKey r, final ColKey c, final Value val) throws WtableCheckedException;

    /**
     * Delete the value by row key and col key.
     *
     * @param r row key
     * @param c col key
     * @throws WtableCheckedException if failed
     */
    void delete(final RowKey r, final ColKey c) throws WtableCheckedException;

    /**
     * Set a value by row key and col key, ignore if exists.
     *
     * @param r row key
     * @param c col key
     * @param val value
     * @return true if not exists and set successfully, false if exists
     * @throws WtableCheckedException if failed
     */
    boolean insertOnNotExists(final RowKey r, final ColKey c, final Value val) throws WtableCheckedException;

    /**
     * Set a value by row key and col key, ignore if not exists.
     *
     * @param r row key
     * @param c col key
     * @param val value
     * @return true if exists and set successfully, false if not exists
     * @throws WtableCheckedException if failed
     */
    boolean updateOnExists(final RowKey r, final ColKey c, final Value val) throws WtableCheckedException;

    /**
     * Set a value by apply a function {@link Function updater} to existed value by row key
     * and col key.
     *
     * If no value exists for given row key and col key, no update operation will be performed and
     * return false; if value exists but {@link Function updater} returns {@code null}, no update
     * operation will be performed and return false; if value exists and {@link Function updater}
     * returns non-null, an update operation will be performed and return true if succeed.
     *
     * @param r row key
     * @param c col key
     * @param updater updater function, accept nullable, output nullable
     * @return true if updated, otherwise false
     * @throws WtableCheckedException if failed
     */
    boolean compareAndUpdate(
            final RowKey r, final ColKey c, final UnaryOperator<Value> updater
    ) throws WtableCheckedException;

    /**
     * Delete a value by apply a predicate {@link Predicate when} to existed value by row key
     * and col key.
     *
     * If no value exists for given row key and col key, no delete operation will be performed and
     * return false; if value exists but {@link Predicate when} returns {@code false}, no delete
     * operation will be performed and return false; if value exists and {@link Predicate when}
     * returns true, a delete operation will be performed and return true if succeed.
     *
     * @param r row key
     * @param c col key
     * @param when test predicate, accept nullable
     * @return true if updated, otherwise false
     * @throws WtableCheckedException if failed
     */
    boolean compareAndDelete(
            final RowKey r, final ColKey c, final Predicate<Value> when
    ) throws WtableCheckedException;
}
