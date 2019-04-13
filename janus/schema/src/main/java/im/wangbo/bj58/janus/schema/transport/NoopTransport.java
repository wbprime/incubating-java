package im.wangbo.bj58.janus.schema.transport;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class NoopTransport implements Transport {
    private static final NoopTransport INSTANCE = new NoopTransport();

    static NoopTransport instance() {
        return INSTANCE;
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        return Futures.completed();
    }

    @Override
    public CompletableFuture<Void> close() {
        return Futures.completed();
    }

    @Override
    public CompletableFuture<Void> send(final Request request) {
        return Futures.completed();
    }

    @Override
    public synchronized Transport handler(final Consumer<JsonObject> handler) {
        return this;
    }

    @Override
    public Transport exceptionHandler(Consumer<Throwable> handler) {
        return this;
    }
}
