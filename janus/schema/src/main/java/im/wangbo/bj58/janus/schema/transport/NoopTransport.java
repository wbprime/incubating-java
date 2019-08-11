package im.wangbo.bj58.janus.schema.transport;

import javax.json.JsonObject;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
    public String name() {
        return "noop";
    }

    @Override
    public boolean accepts(final URI uri) {
        return true;
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> close() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> send(final Request msg) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public Transport handler(final Consumer<JsonObject> handler) {
        return this;
    }

    @Override
    public Transport exceptionHandler(Consumer<Throwable> handler) {
        return this;
    }
}
