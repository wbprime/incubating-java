package im.wangbo.bj58.janus.schema.vertx.http;

import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class Futures {
    private Futures() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    static <T> CompletableFuture<T> illegalState(final String msg) {
        return failed(new IllegalStateException(msg));
    }

    static <T> CompletableFuture<T> illegalArgument(final String msg) {
        return failed(new IllegalArgumentException(msg));
    }

    static <T> CompletableFuture<T> illegalArgument(final String template, final Object... args) {
        return failed(new IllegalArgumentException(String.format(template, args)));
    }

    static <T> CompletableFuture<T> failed(final Throwable ex) {
        final CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(ex);
        return future;
    }

    static <T> CompletableFuture<T> completed() {
        return completed(null);
    }

    static <T> CompletableFuture<T> completed(final T value) {
        return CompletableFuture.completedFuture(value);
    }
}
