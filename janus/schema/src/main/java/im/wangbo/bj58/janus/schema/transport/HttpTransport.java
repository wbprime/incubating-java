package im.wangbo.bj58.janus.schema.transport;

import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.json.JsonObject;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class HttpTransport implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTransport.class);

    private static final int DEFAULT_HTTP_PORT = 80;
    private static final int DEFAULT_HTTPS_PORT = 443;

    private final Vertx vertx;
    private final long pollIntervalInMillis = 500L;

    private List<Consumer<JsonObject>> handlers = Collections.emptyList();
    private Consumer<Throwable> exHandler = ex -> LOG.error("HTTP backend exception", ex);

    private HttpTransportHelper http = HttpTransportHelper.noop();

    static HttpTransport create() {
        return create(Vertx.vertx());
    }

    static HttpTransport create(final Vertx vertx) {
        return new HttpTransport(vertx);
    }

    private HttpTransport(final Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        final String schema = uri.getScheme();

        final HttpClientOptions options = new HttpClientOptions()
                .setDefaultHost(uri.getHost());
        if ("http".equalsIgnoreCase(schema)) {
            options.setSsl(false).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTP_PORT : uri.getPort());
        } else if ("https".equalsIgnoreCase(schema)) {
            options.setSsl(true).setTrustAll(true).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTPS_PORT : uri.getPort());
        } else {
            return Futures.illegalArgument("Unsupported schema \"" + schema + "\" by HttpTransport");
        }

        // Connect to HTTP endpoint
        final HttpClient httpClient = vertx.createHttpClient(options);
        updateBackend(httpClient, uri);

        return Futures.completed();
    }

    @Override
    public CompletableFuture<Void> close() {
        try {
            http.close();
            return Futures.completed();
        } catch (Exception ex) {
            return Futures.failed(ex);
        }
    }

    @Override
    public CompletableFuture<Void> send(final RequestMessage msg) {
        final CompletableFuture<Void> future = new CompletableFuture<>();

        final HttpRequesting requesting = HttpRequesting.create(msg, http);
        requesting.request(msg, future)
                .whenComplete((re, ex) -> {
                    if (null != ex) exHandler.accept(ex);
                    else handlers.forEach(h -> h.accept(re));
                });

        return future;
    }

    @Override
    public synchronized Transport handler(final Consumer<JsonObject> handler) {
        handlers = ImmutableList.<Consumer<JsonObject>>builder()
                .addAll(handlers)
                .add(exceptionSafe(handler))
                .build();
        return this;
    }

    @Override
    public Transport exceptionHandler(final Consumer<Throwable> handler) {
        this.exHandler = handler;
        return this;
    }

    private Consumer<JsonObject> exceptionSafe(final Consumer<JsonObject> c) {
        return json -> {
            try {
                c.accept(json);
            } catch (Throwable ex) {
                exHandler.accept(ex);
            }
        };
    }

    private void updateBackend(final HttpClient client, final URI uri) {
        LOG.debug("HttpTransport backend switched to {} => {}", client, uri);
        http = HttpTransportHelper.std(client, uri.getPath());
    }
}
