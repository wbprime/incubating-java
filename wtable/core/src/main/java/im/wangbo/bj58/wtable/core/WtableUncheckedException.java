package im.wangbo.bj58.wtable.core;

public class WtableUncheckedException extends RuntimeException {
    public WtableUncheckedException(final String op, final Throwable cause) {
        super("Failed wtable operation: " + op, cause);
    }
}
