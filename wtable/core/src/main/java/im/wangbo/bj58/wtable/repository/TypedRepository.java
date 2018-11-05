package im.wangbo.bj58.wtable.repository;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableException;

/**
 * Wrap entity based {@link Repository} operations.
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * There must be function mapped instance of type {@code E} to {@link RowKey},
 * {@link ColKey} and {@link Value} instances.
 *
 * There also must be function mapped instances of {@link RowKey}, {@link ColKey} and {@link Value} to
 * type {@code E} instance.
 *
 * @see Repository
 * @see Repositories.TypedBuilder1
 */
public interface TypedRepository<E> {
    Optional<E> find(final E id) throws WtableException;

    void overrideInsert(final E e) throws WtableException;

    void delete(final E id) throws WtableException;

    boolean insertOnNotExists(final E e) throws WtableException;

    boolean updateOnExists(final E e) throws WtableException;

    /**
     * Note here {@code updater} should handle case when input is null.
     *
     * @param id id entity
     * @param updater update mapper, accept nullable, output nullable
     * @return true if succeed, other false
     * @throws WtableException
     */
    boolean compareAndUpdate(final E id, final UnaryOperator<E> updater) throws WtableException;

    /**
     * Note here {@code updater} should handle case when input is null.
     *
     * @param id id entity
     * @param when test predicate, accept nullable
     * @return true if succeed, otherwise false
     * @throws WtableException
     */
    boolean compareAndDelete(final E id, final Predicate<E> when) throws WtableException;
}
