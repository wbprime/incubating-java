package im.wangbo.bj58.incubating.wtable.repository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import im.wangbo.bj58.incubating.wtable.core.CasLockedValue;
import im.wangbo.bj58.incubating.wtable.core.ColKey;
import im.wangbo.bj58.incubating.wtable.core.RowKey;
import im.wangbo.bj58.incubating.wtable.core.Table;
import im.wangbo.bj58.incubating.wtable.core.Value;
import im.wangbo.bj58.incubating.wtable.core.WtableException;
import im.wangbo.bj58.incubating.wtable.core.WtableStub;

/**
 * TODO add brief description here
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
    public Optional<Value> find(final RowKey r, final ColKey c) {
        try {
            return findByKey(wtable, table, r, c);
        } catch (WtableException ex) {
            // TODO
            return Optional.empty();
        }
    }

    @Override
    public void overrideInsert(final RowKey r, final ColKey c, final Value val) {
        try {
            wtable.set(table, r, c, val);
        } catch (WtableException ex) {
            // TODO
        }
    }

    @Override
    public void delete(final RowKey r, final ColKey c) {
        try {
            wtable.delete(table, r, c);
        } catch (WtableException ex) {
            // TODO
        }
    }

    @Override
    public boolean insertOnNotExists(final RowKey r, final ColKey c, final Value val) {
        final CasLockedValue casLocked;
        try {
            casLocked = wtable.getCasLocked(table, r, c);
        } catch (WtableException ex) {
            // TODO
            throw new RuntimeException(ex);
        }

        if (casLocked.value().isPresent()) {
            return false;
        } else {
            try {
                wtable.set(table, r, c, val, casLocked.casStamp());
            } catch (WtableException ex) {
                // TODO
            }

            return true;
        }
    }

    @Override
    public boolean updateOnExists(final RowKey r, final ColKey c, final Value val) {
        final CasLockedValue casLocked;
        try {
            casLocked = wtable.getCasLocked(table, r, c);
        } catch (WtableException ex) {
            // TODO
            throw new RuntimeException(ex);
        }

        if (casLocked.value().isPresent()) {
            try {
                wtable.set(table, r, c, val, casLocked.casStamp());
            } catch (WtableException ex) {
                // TODO
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean compareAndUpdate(
            final RowKey r, final ColKey c, final Function<Value, Optional<Value>> updater
    ) {
        final CasLockedValue casLocked;
        try {
            casLocked = wtable.getCasLocked(table, r, c);
        } catch (WtableException ex) {
            // TODO
            throw new RuntimeException(ex);
        }

        final Optional<Value> newVal = casLocked.value().flatMap(updater);
        if (newVal.isPresent()) {
            try {
                wtable.set(table, r, c, newVal.get(), casLocked.casStamp());
            } catch (WtableException ex) {
                // TODO
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean compareAndDelete(
            final RowKey r, final ColKey c, final Predicate<Value> when
    ) {
        final CasLockedValue casLocked;
        try {
            casLocked = wtable.getCasLocked(table, r, c);
        } catch (WtableException ex) {
            // TODO
            throw new RuntimeException(ex);
        }

        final Optional<Value> newVal = casLocked.value().filter(when);
        if (newVal.isPresent()) {
            try {
                wtable.delete(table, r, c, casLocked.casStamp());
            } catch (WtableException ex) {
                // TODO
            }

            return true;
        } else {
            return false;
        }
    }
}
