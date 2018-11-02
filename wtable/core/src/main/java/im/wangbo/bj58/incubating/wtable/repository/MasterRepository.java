package im.wangbo.bj58.incubating.wtable.repository;

import java.util.Optional;

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
final class MasterRepository extends AbstractRepositoryImpl {
    MasterRepository(WtableStub wtable, Table table) {
        super(wtable, table);
    }

    @Override
    Optional<Value> findByKey(WtableStub wtableStub, Table table, RowKey r, ColKey c) throws WtableException {
        return wtableStub.getMasterOnly(table, r, c);
    }

    @Override
    public String toString() {
        return "MasterRepository{}";
    }
}
