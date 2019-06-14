package im.wangbo.bj58.wtable.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Table;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableCheckedException;
import im.wangbo.bj58.wtable.core.WtableStub;
import im.wangbo.bj58.wtable.core.Wtables;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class SlaveRepositoryTest {
    private Random random = new Random();

    private WtableStub stub;

    private Table table;

    private Repository repository;

    @Before
    public void setUp() throws Exception {
        random = new Random();

        final int tableId = random.nextInt(100);

        stub = Mockito.mock(WtableStub.class);
        table = Table.of(tableId);

        repository = new SlaveRepository(stub, table);
    }

    @Test
    public void find_exists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        when(stub.getMasterSlave(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(Optional.of(value));

        final Optional<Value> result = repository.find(rowKey, colKey);

        Assertions.assertThat(result).isPresent().hasValue(value);

        verify(stub).getMasterSlave(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void find_nonExists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        when(stub.getMasterSlave(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(Optional.empty());

        final Optional<Value> result = repository.find(rowKey, colKey);

        Assertions.assertThat(result).isEmpty();

        verify(stub).getMasterSlave(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void find_ex() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        when(stub.getMasterSlave(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenThrow(new WtableCheckedException("get", table, rowKey, colKey, new RuntimeException("Ex")));

        Assertions.assertThatThrownBy(() -> repository.find(rowKey, colKey))
                .isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getMasterSlave(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

}