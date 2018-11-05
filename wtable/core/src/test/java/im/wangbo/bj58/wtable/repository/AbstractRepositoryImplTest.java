package im.wangbo.bj58.wtable.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import im.wangbo.bj58.wtable.core.CasLockedValue;
import im.wangbo.bj58.wtable.core.CasStamp;
import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Table;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableCheckedException;
import im.wangbo.bj58.wtable.core.WtableStub;
import im.wangbo.bj58.wtable.core.Wtables;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class AbstractRepositoryImplTest {
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

        repository = new AbstractRepositoryImpl(stub, table) {
            @Override
            Optional<Value> findByKey(WtableStub wtableStub, Table table, RowKey r, ColKey c) throws WtableCheckedException {
                throw new UnsupportedOperationException("Not implemented yet");
            }
        };
    }

    @Test
    public void overrideInsert_success() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        doNothing().when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        repository.overrideInsert(rowKey, colKey, value);

        verify(stub).set(table, rowKey, colKey, value);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void overrideInsert_ex() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        doThrow(new WtableCheckedException("set", table, new RuntimeException("Ex"))).when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        Assertions.assertThatThrownBy(() -> repository.overrideInsert(rowKey, colKey, value))
                .isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).set(table, rowKey, colKey, value);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void delete_success() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        doNothing().when(stub).delete(
                any(Table.class), any(RowKey.class), any(ColKey.class)
        );

        repository.delete(rowKey, colKey);

        verify(stub).delete(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void delete_ex() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        doThrow(new WtableCheckedException("delete", table, new RuntimeException("Ex"))).when(stub).delete(
                any(Table.class), any(RowKey.class), any(ColKey.class)
        );

        Assertions.assertThatThrownBy(() -> repository.delete(rowKey, colKey))
                .isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).delete(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void insertOnNotExists_exists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        final boolean result = repository.insertOnNotExists(rowKey, colKey, value);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void insertOnNotExists_nonExists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doNothing().when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        final boolean result = repository.insertOnNotExists(rowKey, colKey, value);

        Assertions.assertThat(result).isTrue();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void insertOnNotExists_exGet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenThrow(new WtableCheckedException("getCasLocked", table, rowKey, colKey, new RuntimeException("Ex")));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class));

        Assertions.assertThatThrownBy(
                () -> repository.insertOnNotExists(rowKey, colKey, value)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void insertOnNotExists_exExistsSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doThrow(new WtableCheckedException("set", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final boolean result = repository.insertOnNotExists(rowKey, colKey, value);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void insertOnNotExists_exNonExistsSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doThrow(new WtableCheckedException("set", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        Assertions.assertThatThrownBy(
                () -> repository.insertOnNotExists(rowKey, colKey, value)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void updateOnExists_exists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        final boolean result = repository.updateOnExists(rowKey, colKey, value);

        Assertions.assertThat(result).isTrue();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void updateOnExists_nonExists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doNothing().when(stub).set(
                any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class)
        );

        final boolean result = repository.updateOnExists(rowKey, colKey, value);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void updateOnExists_exGet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenThrow(new WtableCheckedException("getCasLocked", table, rowKey, colKey, new RuntimeException("Ex")));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class));

        Assertions.assertThatThrownBy(
                () -> repository.updateOnExists(rowKey, colKey, value)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void updateOnExists_exExistsSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doThrow(new WtableCheckedException("set", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        Assertions.assertThatThrownBy(
                () -> repository.updateOnExists(rowKey, colKey, value)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void updateOnExists_exNonExistsSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doThrow(new WtableCheckedException("set", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final boolean result = repository.updateOnExists(rowKey, colKey, value);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
    }

    @Test
    public void compareAndUpdate_nonExists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class));

        final UnaryOperator<Value> updater = mock(UnaryOperator.class);

        final boolean result = repository.compareAndUpdate(rowKey, colKey,  updater);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verifyZeroInteractions(updater);
    }

    @Test
    public void compareAndUpdate_existsOperatorReturnsNull() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final UnaryOperator<Value> updater = mock(UnaryOperator.class);
        when(updater.apply(any(Value.class))).thenReturn(null);

        final boolean result = repository.compareAndUpdate(rowKey, colKey, updater);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verify(updater).apply(oldValue);
        verifyNoMoreInteractions(updater);
    }

    @Test
    public void compareAndUpdate_existsOperatorReturnsNonNull() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final UnaryOperator<Value> updater = mock(UnaryOperator.class);
        when(updater.apply(any(Value.class))).thenReturn(value);

        final boolean result = repository.compareAndUpdate(rowKey, colKey, updater);

        Assertions.assertThat(result).isTrue();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
        verify(updater).apply(oldValue);
        verifyNoMoreInteractions(updater);
    }

    @Test
    public void compareAndUpdate_existsExGet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenThrow(new WtableCheckedException("getCasLocked", table, rowKey, colKey, new RuntimeException("Ex")));
        doNothing().when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final UnaryOperator<Value> updater = mock(UnaryOperator.class);
        when(updater.apply(any(Value.class))).thenReturn(value);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndUpdate(rowKey, colKey, updater)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);


        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verifyNoMoreInteractions(updater);
    }

    @Test
    public void compareAndUpdate_existsExSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doThrow(new WtableCheckedException("set", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).set(any(Table.class), any(RowKey.class), any(ColKey.class), any(Value.class), any(CasStamp.class));

        final UnaryOperator<Value> updater = mock(UnaryOperator.class);
        when(updater.apply(any(Value.class))).thenReturn(value);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndUpdate(rowKey, colKey, updater)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).set(table, rowKey, colKey, value, casStamp);
        verifyNoMoreInteractions(stub);
        verify(updater).apply(oldValue);
        verifyNoMoreInteractions(updater);
    }

    @Test
    public void compareAndDelete_nonExists() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(casStamp));
        doNothing().when(stub).delete(any(Table.class), any(RowKey.class), any(ColKey.class), any(CasStamp.class));

        final Predicate<Value> predicate = mock(Predicate.class);

        final boolean result = repository.compareAndDelete(rowKey, colKey,  predicate);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verifyZeroInteractions(predicate);
    }

    @Test
    public void compareAndDelete_existsPredicateReturnsFalse() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).delete(any(Table.class), any(RowKey.class), any(ColKey.class), any(CasStamp.class));

        final Predicate<Value> predicate = mock(Predicate.class);
        when(predicate.test(any(Value.class))).thenReturn(false);

        final boolean result = repository.compareAndDelete(rowKey, colKey, predicate);

        Assertions.assertThat(result).isFalse();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verify(predicate).test(oldValue);
        verifyNoMoreInteractions(predicate);
    }

    @Test
    public void compareAndDelete_existsPredicateReturnsTrue() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doNothing().when(stub).delete(any(Table.class), any(RowKey.class), any(ColKey.class), any(CasStamp.class));

        final Predicate<Value> predicate = mock(Predicate.class);
        when(predicate.test(any(Value.class))).thenReturn(true);

        final boolean result = repository.compareAndDelete(rowKey, colKey, predicate);

        Assertions.assertThat(result).isTrue();

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).delete(table, rowKey, colKey, casStamp);
        verifyNoMoreInteractions(stub);
        verify(predicate).test(oldValue);
        verifyNoMoreInteractions(predicate);
    }

    @Test
    public void compareAndDelete_existsExGet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenThrow(new WtableCheckedException("getCasLocked", table, rowKey, colKey, new RuntimeException("Ex")));
        doNothing().when(stub).delete(any(Table.class), any(RowKey.class), any(ColKey.class), any(CasStamp.class));

        final Predicate<Value> predicate = mock(Predicate.class);
        when(predicate.test(any(Value.class))).thenReturn(true);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndDelete(rowKey, colKey, predicate)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);


        verify(stub).getCasLocked(table, rowKey, colKey);
        verifyNoMoreInteractions(stub);
        verifyNoMoreInteractions(predicate);
    }

    @Test
    public void compareAndDelete_existsExSet() throws Exception {
        final RowKey rowKey = Wtables.rowKey(String.valueOf(random.nextLong()));
        final ColKey colKey = Wtables.colKey(String.valueOf(random.nextLong()));
        final Value value = Wtables.value(String.valueOf(random.nextLong()));

        final Value oldValue = Wtables.value(String.valueOf(random.nextLong()));
        final CasStamp casStamp = CasStamp.of(random.nextInt(Integer.MAX_VALUE));

        when(stub.getCasLocked(any(Table.class), any(RowKey.class), any(ColKey.class)))
                .thenReturn(CasLockedValue.create(oldValue, casStamp));
        doThrow(new WtableCheckedException("delete", table, rowKey, colKey, new RuntimeException("Ex")))
                .when(stub).delete(any(Table.class), any(RowKey.class), any(ColKey.class), any(CasStamp.class));

        final Predicate<Value> predicate = mock(Predicate.class);
        when(predicate.test(any(Value.class))).thenReturn(true);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndDelete(rowKey, colKey, predicate)
        ).isInstanceOf(WtableCheckedException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        verify(stub).getCasLocked(table, rowKey, colKey);
        verify(stub).delete(table, rowKey, colKey, casStamp);
        verifyNoMoreInteractions(stub);
        verify(predicate).test(oldValue);
        verifyNoMoreInteractions(predicate);
    }
}