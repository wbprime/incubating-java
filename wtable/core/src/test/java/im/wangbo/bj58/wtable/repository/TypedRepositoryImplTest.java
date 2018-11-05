package im.wangbo.bj58.wtable.repository;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import im.wangbo.bj58.wtable.core.ColKey;
import im.wangbo.bj58.wtable.core.RowKey;
import im.wangbo.bj58.wtable.core.Value;
import im.wangbo.bj58.wtable.core.WtableException;
import im.wangbo.bj58.wtable.core.Wtables;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;


/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class TypedRepositoryImplTest {
    private Random random = new Random();

    private Repository delegate;
    private TypedRepository<Entity> repository;

    static class Entity {
        String rowK;
        String colK;

        int index;
        String name;
        long size;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
            return index == entity.index &&
                    size == entity.size &&
                    Objects.equals(rowK, entity.rowK) &&
                    Objects.equals(colK, entity.colK) &&
                    Objects.equals(name, entity.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowK, colK, index, name, size);
        }
    }

    private static RowKey entityToRow(final Entity e) {
        return Wtables.rowKey(e.rowK);
    }

    private static ColKey entityToCol(final Entity e) {
        return Wtables.colKey(e.colK);
    }

    private static Value entityToValue(final Entity e) {
        return Wtables.value(e.index + ":" + e.name, e.size);
    }

    private static Entity backToEntity(final RowKey r, final ColKey c, final Value v) {
        final Entity e = new Entity();

        e.rowK = new String(r.rowKey(), StandardCharsets.UTF_8);
        e.colK = new String(c.colKey(), StandardCharsets.UTF_8);

        final String vv = new String(v.value(), StandardCharsets.UTF_8);
        final int idx = vv.indexOf(":");

        e.index = Ints.tryParse(vv.substring(0, idx));
        e.name = vv.substring(idx + 1);
        e.size = v.score();

        return e;
    }

    private static Entity generateEntity(final Random r) {
        final Entity e = new Entity();
        e.rowK = "r_" + r.nextInt(Integer.MAX_VALUE);
        e.colK = "c_" + r.nextInt(Integer.MAX_VALUE);
        e.name = "name_" + r.nextInt(Integer.MAX_VALUE);
        e.index = r.nextInt(100);
        e.size = r.nextInt(100000);

        return e;
    }

    @Before
    public void setUp() throws Exception {
        random = new Random();

        delegate = Mockito.mock(Repository.class);

        repository = new TypedRepositoryImpl<>(
                delegate,
                TypedRepositoryImplTest::entityToRow,
                TypedRepositoryImplTest::entityToCol,
                TypedRepositoryImplTest::entityToValue,
                TypedRepositoryImplTest::backToEntity
        );
    }

    @Test
    public void find_found() throws Exception {
        final Entity e1 = generateEntity(random);

        final Entity entity = generateEntity(random);
        entity.rowK = e1.rowK;
        entity.colK = e1.colK;

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(entity);
        Mockito.when(delegate.find(rowKey, colKey)).thenReturn(Optional.of(value));

        final Optional<Entity> result = repository.find(e1);

        Assertions.assertThat(result).isPresent().hasValue(entity);

        Mockito.verify(delegate).find(rowKey, colKey);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void find_notFound() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        Mockito.when(delegate.find(rowKey, colKey)).thenReturn(Optional.empty());

        final Optional<Entity> result = repository.find(e1);

        Assertions.assertThat(result).isEmpty();

        Mockito.verify(delegate).find(rowKey, colKey);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void find_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        Mockito.when(delegate.find(rowKey, colKey))
                .thenThrow(new WtableException("find", new RuntimeException("Ex")));

        Assertions.assertThatThrownBy(
                () -> repository.find(e1)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).find(rowKey, colKey);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void overrideInsert_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.doThrow(new WtableException("find", new RuntimeException("Ex")))
                .when(delegate).overrideInsert(rowKey, colKey, value);

        Assertions.assertThatThrownBy(
                () -> repository.overrideInsert(e1)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).overrideInsert(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void overrideInsert_success() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.doNothing().when(delegate).overrideInsert(rowKey, colKey, value);

        repository.overrideInsert(e1);

        Mockito.verify(delegate).overrideInsert(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void delete_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        Mockito.doThrow(new WtableException("find", new RuntimeException("Ex")))
                .when(delegate).delete(rowKey, colKey);

        Assertions.assertThatThrownBy(
                () -> repository.delete(e1)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).delete(rowKey, colKey);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void delete_success() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        Mockito.doNothing().when(delegate).delete(rowKey, colKey);

        repository.delete(e1);

        Mockito.verify(delegate).delete(rowKey, colKey);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void insertOnNotExists_true() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.insertOnNotExists(rowKey, colKey, value))
                .thenReturn(true);

        final boolean result = repository.insertOnNotExists(e1);

        Assertions.assertThat(result).isTrue();

        Mockito.verify(delegate).insertOnNotExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void insertOnNotExists_false() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.insertOnNotExists(rowKey, colKey, value))
                .thenReturn(false);

        final boolean result = repository.insertOnNotExists(e1);

        Assertions.assertThat(result).isFalse();

        Mockito.verify(delegate).insertOnNotExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void insertOnNotExists_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.insertOnNotExists(rowKey, colKey, value))
                .thenThrow(new WtableException("find", new RuntimeException("Ex")));

        Assertions.assertThatThrownBy(
                () -> repository.insertOnNotExists(e1)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).insertOnNotExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void updateOnExists_true() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.updateOnExists(rowKey, colKey, value))
                .thenReturn(true);

        final boolean result = repository.updateOnExists(e1);

        Assertions.assertThat(result).isTrue();

        Mockito.verify(delegate).updateOnExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void updateOnExists_false() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.updateOnExists(rowKey, colKey, value))
                .thenReturn(false);

        final boolean result = repository.updateOnExists(e1);

        Assertions.assertThat(result).isFalse();

        Mockito.verify(delegate).updateOnExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void updateOnExists_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);
        final Value value = entityToValue(e1);

        Mockito.when(delegate.updateOnExists(rowKey, colKey, value))
                .thenThrow(new WtableException("find", new RuntimeException("Ex")));

        Assertions.assertThatThrownBy(
                () -> repository.updateOnExists(e1)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).updateOnExists(rowKey, colKey, value);
        Mockito.verifyNoMoreInteractions(delegate);
    }

    private static class Mapper implements UnaryOperator<Value> {
        private final long id = System.currentTimeMillis();

        private final Entity entity;

        Mapper(final Entity entity) {
            this.entity = entity;
        }

        @Override
        public Value apply(final Value value) {
            return entityToValue(entity);
        }

        @Override
        public int hashCode() {
            return Longs.hashCode(id);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (null == obj || getClass() != obj.getClass()) return false;

            final Mapper that = getClass().cast(obj);
            return id == that.id;
        }
    }

    @Test
    public void compareAndUpdate_true() throws Exception {
        final Entity e1 = generateEntity(random);

        final Entity entity = generateEntity(random);
        entity.rowK = e1.rowK;
        entity.colK = e1.colK;

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final UnaryOperator<Entity> mapper2 = e -> entity;

        Mockito.when(delegate.compareAndUpdate(any(), any(), any())).thenReturn(true);

        final ArgumentCaptor<UnaryOperator<Value>> mapperArg = ArgumentCaptor.forClass(UnaryOperator.class);

        final boolean result = repository.compareAndUpdate(e1, mapper2);

        Assertions.assertThat(result).isTrue();

        Mockito.verify(delegate).compareAndUpdate(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final UnaryOperator<Value> mapper = mapperArg.getValue();
            final Value newValue = mapper.apply(entityToValue(e1));
            final Entity newEntity = backToEntity(rowKey, colKey, newValue);
            Assertions.assertThat(newEntity).isNotNull();
            Assertions.assertThat(newEntity.rowK).isEqualTo(entity.rowK);
            Assertions.assertThat(newEntity.colK).isEqualTo(entity.colK);
            Assertions.assertThat(newEntity.name).isEqualTo(entity.name);
            Assertions.assertThat(newEntity.index).isEqualTo(entity.index);
            Assertions.assertThat(newEntity.size).isEqualTo(entity.size);
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void compareAndUpdate_false() throws Exception {
        final Entity e1 = generateEntity(random);

        final Entity entity = generateEntity(random);
        entity.rowK = e1.rowK;
        entity.colK = e1.colK;

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final UnaryOperator<Entity> mapper2 = e -> entity;

        Mockito.when(delegate.compareAndUpdate(any(), any(), any())).thenReturn(false);

        final ArgumentCaptor<UnaryOperator<Value>> mapperArg = ArgumentCaptor.forClass(UnaryOperator.class);

        final boolean result = repository.compareAndUpdate(e1, mapper2);

        Assertions.assertThat(result).isFalse();

        Mockito.verify(delegate).compareAndUpdate(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final UnaryOperator<Value> mapper = mapperArg.getValue();
            final Value newValue = mapper.apply(entityToValue(e1));
            final Entity newEntity = backToEntity(rowKey, colKey, newValue);
            Assertions.assertThat(newEntity).isNotNull();
            Assertions.assertThat(newEntity.rowK).isEqualTo(entity.rowK);
            Assertions.assertThat(newEntity.colK).isEqualTo(entity.colK);
            Assertions.assertThat(newEntity.name).isEqualTo(entity.name);
            Assertions.assertThat(newEntity.index).isEqualTo(entity.index);
            Assertions.assertThat(newEntity.size).isEqualTo(entity.size);
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void compareAndUpdate_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final Entity entity = generateEntity(random);
        entity.rowK = e1.rowK;
        entity.colK = e1.colK;

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final UnaryOperator<Entity> mapper2 = e -> entity;

        Mockito.when(delegate.compareAndUpdate(any(), any(), any()))
                .thenThrow(new WtableException("find", new RuntimeException("Ex")));

        final ArgumentCaptor<UnaryOperator<Value>> mapperArg = ArgumentCaptor.forClass(UnaryOperator.class);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndUpdate(e1, mapper2)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).compareAndUpdate(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final UnaryOperator<Value> mapper = mapperArg.getValue();
            final Value newValue = mapper.apply(entityToValue(e1));
            final Entity newEntity = backToEntity(rowKey, colKey, newValue);
            Assertions.assertThat(newEntity).isNotNull();
            Assertions.assertThat(newEntity.rowK).isEqualTo(entity.rowK);
            Assertions.assertThat(newEntity.colK).isEqualTo(entity.colK);
            Assertions.assertThat(newEntity.name).isEqualTo(entity.name);
            Assertions.assertThat(newEntity.index).isEqualTo(entity.index);
            Assertions.assertThat(newEntity.size).isEqualTo(entity.size);
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void compareAndDelete_true() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final Predicate<Entity> mapper2 = e -> e.equals(e1);

        Mockito.when(delegate.compareAndDelete(any(), any(), any())).thenReturn(true);

        final ArgumentCaptor<Predicate<Value>> mapperArg = ArgumentCaptor.forClass(Predicate.class);

        final boolean result = repository.compareAndDelete(e1, mapper2);

        Assertions.assertThat(result).isTrue();

        Mockito.verify(delegate).compareAndDelete(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final Predicate<Value> mapper = mapperArg.getValue();
            final boolean re = mapper.test(entityToValue(e1));
            Assertions.assertThat(re).isTrue();
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void compareAndDelete_false() throws Exception {
        final Entity e1 = generateEntity(random);

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final Predicate<Entity> mapper2 = e -> e.equals(e1);

        Mockito.when(delegate.compareAndDelete(any(), any(), any()))
                .thenReturn(false);

        final ArgumentCaptor<Predicate<Value>> mapperArg = ArgumentCaptor.forClass(Predicate.class);

        final boolean result = repository.compareAndDelete(e1, mapper2);

        Assertions.assertThat(result).isFalse();

        Mockito.verify(delegate).compareAndDelete(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final Predicate<Value> mapper = mapperArg.getValue();
            final boolean re = mapper.test(entityToValue(e1));
            Assertions.assertThat(re).isTrue();
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }

    @Test
    public void compareAndDelete_ex() throws Exception {
        final Entity e1 = generateEntity(random);

        final Entity entity = generateEntity(random);
        entity.rowK = e1.rowK;
        entity.colK = e1.colK;

        final RowKey rowKey = entityToRow(e1);
        final ColKey colKey = entityToCol(e1);

        final Predicate<Entity> mapper2 = e -> e.equals(e1);

        Mockito.when(delegate.compareAndDelete(any(), any(), any()))
                .thenThrow(new WtableException("find", new RuntimeException("Ex")));

        final ArgumentCaptor<Predicate<Value>> mapperArg = ArgumentCaptor.forClass(Predicate.class);

        Assertions.assertThatThrownBy(
                () -> repository.compareAndDelete(e1, mapper2)
        ).isInstanceOf(WtableException.class)
                .hasCauseInstanceOf(RuntimeException.class);

        Mockito.verify(delegate).compareAndDelete(eq(rowKey), eq(colKey), mapperArg.capture());
        {
            final Predicate<Value> mapper = mapperArg.getValue();
            final boolean re = mapper.test(entityToValue(e1));
            Assertions.assertThat(re).isTrue();
        }
        Mockito.verifyNoMoreInteractions(delegate);
    }
}