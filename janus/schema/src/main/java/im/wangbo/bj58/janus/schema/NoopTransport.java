package im.wangbo.bj58.janus.schema;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class NoopTransport implements Transport {
    private static class SingletonHolder {
        private static final NoopTransport INSTANCE = new NoopTransport();

        static NoopTransport instance() {
            return INSTANCE;
        }
    }

    static NoopTransport instance() {
        return SingletonHolder.instance();
    }

    @Override
    public CompletableFuture<Void> connect(URI uri) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> close() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> request(final JsonObject request) {
        return CompletableFuture.completedFuture(null);
    }
}
