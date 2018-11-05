package im.wangbo.bj58.wtable.core;

import javax.annotation.Nullable;

public class WtableException extends Exception {
    private String op;
    private Table table;

    @Nullable
    private RowKey rowKey;
    @Nullable
    private ColKey colKey;
    @Nullable
    private Value value;
    @Nullable
    private CasStamp casStamp;

    public WtableException(
            final String op,
            @Nullable final Table table,
            final Throwable cause
    ) {
        this(op, table, null, null, cause);
    }

    public WtableException(
            final String op,
            @Nullable final Table table,
            @Nullable final RowKey rowKey,
            @Nullable final ColKey colKey,
            final Throwable cause
    ) {
        this(op, table, rowKey, colKey, null, null, cause);
    }

    public WtableException(
            final String op,
            @Nullable final Table table,
            @Nullable final RowKey rowKey,
            @Nullable final ColKey colKey,
            @Nullable final Value value,
            @Nullable final CasStamp casStamp,
            final Throwable cause
    ) {
        super("Failed to perform wtable operation " + op, cause);
        this.op = op;
        this.table = table;
        this.rowKey = rowKey;
        this.colKey = colKey;
        this.value = value;
        this.casStamp = casStamp;
    }
}
