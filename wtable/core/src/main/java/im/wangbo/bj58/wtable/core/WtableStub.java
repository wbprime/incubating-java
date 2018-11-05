package im.wangbo.bj58.wtable.core;

import java.util.Optional;

public interface WtableStub {
    /**
     * Ping the wtable server.
     *
     * @throws WtableCheckedException if failed
     */
    void ping() throws WtableCheckedException;

    /**
     * Get value from salve.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @return value if found, otherwise {@link Optional#empty()}
     * @throws WtableCheckedException if failed
     */
    Optional<Value> getMasterSlave(
            final Table table, final RowKey rowKey, final ColKey colKey
    ) throws WtableCheckedException;
    /**
     * Get value from master.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @return value if found, otherwise {@link Optional#empty()}
     * @throws WtableCheckedException if failed
     */
    Optional<Value> getMasterOnly(
            final Table table, final RowKey rowKey, final ColKey colKey
    ) throws WtableCheckedException;
    /**
     * Get value from master with cas locked.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @return value with cas
     * @throws WtableCheckedException if failed
     */
    CasLockedValue getCasLocked(
            final Table table, final RowKey rowKey, final ColKey colKey
    ) throws WtableCheckedException;

    /**
     * Set value without cas lock.
     *
     * Set a new value for given row key and col key if no value exists, otherwise override it.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param value value
     * @throws WtableCheckedException if failed
     */
    default void set(
            final Table table, final RowKey rowKey, final ColKey colKey, final Value value
    ) throws WtableCheckedException {
        set(table, rowKey, colKey, value, CasStamp.of(0));
    }
    /**
     * Set value with cas lock got by {@link #getCasLocked(Table, RowKey, ColKey)}.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param value value
     * @param cas cas stamp
     * @throws WtableCheckedException if failed
     */
    void set(
            final Table table,
            final RowKey rowKey, final ColKey colKey, final Value value,
            final CasStamp cas
    ) throws WtableCheckedException;

    /**
     * Delete value without cas lock.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @throws WtableCheckedException if failed
     */
    default void delete(final Table table, final RowKey rowKey, final ColKey colKey) throws WtableCheckedException {
        delete(table, rowKey, colKey, CasStamp.of(0));
    }
    /**
     * Delete value with cas lock got by {@link #getCasLocked(Table, RowKey, ColKey)}.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param cas cas stamp
     * @throws WtableCheckedException if failed
     */
    void delete(
            final Table table, final RowKey rowKey, final ColKey colKey, final CasStamp cas
    ) throws WtableCheckedException;

    /**
     * Increase value by {@code step} without cas lock.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param step increasing step value
     * @throws WtableCheckedException if failed
     */
    default long increase(
            final Table table, final RowKey rowKey, final ColKey colKey, final long step
    ) throws WtableCheckedException {
        return increase(table, rowKey, colKey, CasStamp.of(0), step);
    }
    /**
     * Increase value by {@code step} with cas lock got by {@link #getCasLocked(Table, RowKey, ColKey)}.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param cas cas stamp
     * @param step increasing step value
     * @throws WtableCheckedException if failed
     */
    long increase(
            final Table table, final RowKey rowKey, final ColKey colKey,
            final CasStamp cas, final long step
    ) throws WtableCheckedException;

    /**
     * Scan wtable as an iterable.
     *
     * 1. note that the iterate operation on returned iterable is not thread safe.
     * 2. note that the iterate operation may throw {@link IllegalStateException} due to internal wtable
     *  op failure.
     *
     * @param table table
     * @param rowKey row key
     * @param ascend sort order
     * @return scanned results
     * @implNote the internal {@code Iterable} may be not thread safe
     * @implNote the internal {@code Iterable} may throw {@link WtableUncheckedException} if failed
     */
    Iterable<ScannedItem> scan(
            final Table table, final RowKey rowKey, final boolean ascend
    );
    /**
     * Scan wtable as an iterable.
     *
     * 1. note that the iterate operation on returned iterable is not thread safe.
     * 2. note that the iterate operation may throw {@link IllegalStateException} due to internal wtable
     *  op failure.
     *
     * @param table table
     * @param rowKey row key
     * @param colKey col key
     * @param ascend sort order
     * @return scanned results
     * @implNote the internal {@code Iterable} may be not thread safe
     * @implNote the internal {@code Iterable} may throw {@link WtableUncheckedException} if failed
     */
    Iterable<ScannedItem> scan(
            final Table table, final RowKey rowKey, final ColKey colKey, final boolean ascend
    );

    /**
     * Dump wtable as an iterable.
     *
     * 1. note that the iterate operation on returned iterable is not thread safe.
     * 2. note that the iterate operation may throw {@link IllegalStateException} due to internal wtable
     *  op failure.
     *
     * @return dumped results
     * @implNote the internal {@code Iterable} may be not thread safe
     * @implNote the internal {@code Iterable} may throw {@link WtableUncheckedException} if failed
     */
    Iterable<DumpedItem> dumpAll();
    /**
     * Dump wtable as an iterable.
     *
     * 1. note that the iterate operation on returned iterable is not thread safe.
     * 2. note that the iterate operation may throw {@link IllegalStateException} due to internal wtable
     *  op failure.
     *
     * @param table table
     * @return dumped results
     * @implNote the internal {@code Iterable} may be not thread safe
     * @implNote the internal {@code Iterable} may throw {@link WtableUncheckedException} if failed
     */
    Iterable<DumpedItem> dumpTable(final Table table);
}
