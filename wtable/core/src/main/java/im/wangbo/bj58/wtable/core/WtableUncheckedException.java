package im.wangbo.bj58.wtable.core;

import javax.annotation.Nullable;

public class WtableUncheckedException extends RuntimeException {
    private Table table;

    @Nullable
    private RowKey rowKey;
    @Nullable
    private ColKey colKey;
    @Nullable
    private Value value;
    @Nullable
    private CasStamp casStamp;

    public WtableUncheckedException(
            final String op,
            final Throwable cause
    ) {
        this(op, null, cause);
    }

    public WtableUncheckedException(
            final String op,
            @Nullable final Table table,
            final Throwable cause
    ) {
        this(op, table, null, null, cause);
    }

    public WtableUncheckedException(
            final String op,
            @Nullable final Table table,
            @Nullable final RowKey rowKey,
            @Nullable final ColKey colKey,
            final Throwable cause
    ) {
        this(op, table, rowKey, colKey, null, cause);
    }

    public WtableUncheckedException(
            final String op,
            @Nullable final Table table,
            @Nullable final RowKey rowKey,
            @Nullable final ColKey colKey,
            @Nullable final Value value,
            final Throwable cause
    ) {
        super(
                String.format(
                        "Failed to perform operation \"%s\", in Table(%s), by Row(%s) Col(%s), optionally Value(%s)",
                        op, table, rowKey, colKey, value
                ),
                cause
        );
        this.table = table;
        this.rowKey = rowKey;
        this.colKey = colKey;
        this.value = value;
    }

    public Table getTable() {
        return table;
    }

    @Nullable
    public RowKey getRowKey() {
        return rowKey;
    }

    @Nullable
    public ColKey getColKey() {
        return colKey;
    }

    @Nullable
    public Value getValue() {
        return value;
    }
}
