package im.wangbo.bj58.wtable.repository;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import im.wangbo.bj58.wtable.core.CasLockedValue;
import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Table;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableException;
import im.wangbo.bj58.wtable.core.WtableStub;

/**
 * Implement {@link Repository}.
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
abstract class AbstractRepositoryImpl implements Repository {
    private final WtableStub wtable;

    private final Table table;

    AbstractRepositoryImpl(
            final WtableStub wtable,
            final Table table
    ) {
        this.wtable = wtable;
        this.table = table;
    }

    // Master-slave or master-only
    abstract Optional<Value> findByKey(
            final WtableStub wtableStub, final Table table, final RowKey r, final ColKey c
    ) throws WtableException;

    @Override
    public Optional<Value> find(final RowKey r, final ColKey c) throws WtableException {
        return findByKey(wtable, table, r, c);
    }

    @Override
    public void overrideInsert(final RowKey r, final ColKey c, final Value val) throws WtableException {
        wtable.set(table, r, c, val);
    }

    @Override
    public void delete(final RowKey r, final ColKey c) throws WtableException {
        wtable.delete(table, r, c);
    }

    @Override
    public boolean insertOnNotExists(final RowKey r, final ColKey c, final Value val) throws WtableException {
        final CasLockedValue casLocked = wtable.getCasLocked(table, r, c);
        if (casLocked.value().isPresent()) {
            return false;
        } else {
            wtable.set(table, r, c, val, casLocked.casStamp());
            return true;
        }
    }

    @Override
    public boolean updateOnExists(final RowKey r, final ColKey c, final Value val) throws WtableException {
        final CasLockedValue casLocked = wtable.getCasLocked(table, r, c);
        if (casLocked.value().isPresent()) {
            wtable.set(table, r, c, val, casLocked.casStamp());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean compareAndUpdate(
            final RowKey r, final ColKey c, final UnaryOperator<Value> updater
    ) throws WtableException {
        final CasLockedValue casLocked = wtable.getCasLocked(table, r, c);

        final Optional<Value> newVal = casLocked.value().map(updater);
        if (newVal.isPresent()) {
            wtable.set(table, r, c, newVal.get(), casLocked.casStamp());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean compareAndDelete(
            final RowKey r, final ColKey c, final Predicate<Value> when
    ) throws WtableException {
        final CasLockedValue casLocked = wtable.getCasLocked(table, r, c);

        final Optional<Value> newVal = casLocked.value().filter(when);
        if (newVal.isPresent()) {
            wtable.delete(table, r, c, casLocked.casStamp());
            return true;
        } else {
            return false;
        }
    }
}
