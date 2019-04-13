package im.wangbo.bj58.janus.schema.transport;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class HttpTransport implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTransport.class);

    private static final int DEFAULT_HTTP_PORT = 80;
    private static final int DEFAULT_HTTPS_PORT = 443;

    private List<Consumer<JsonObject>> handlers = Collections.emptyList();
    private Consumer<Throwable> exHandler = ex -> LOG.error("HTTP backend exception", ex);

    private HttpTransportHelper http = HttpTransportHelper.noop();

    static HttpTransport create() {
        return new HttpTransport();
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        final String schema = uri.getScheme();

        final HttpClientOptions options = new HttpClientOptions().setDefaultHost(uri.getHost());
        if ("http".equalsIgnoreCase(schema)) {
            options.setSsl(false).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTP_PORT : uri.getPort());
        } else if ("https".equalsIgnoreCase(schema)) {
            options.setSsl(true).setTrustAll(true).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTPS_PORT : uri.getPort());
        } else {
            return Futures.illegalArgument("Unsupported schema \"" + schema + "\" by HttpTransport");
        }

        // Connect to HTTP endpoint
        final HttpClient httpClient = Vertx.vertx().createHttpClient(options);
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
    public CompletableFuture<Void> send(final Request body) {
        return request(body).thenCompose(req -> send(req, body));
    }

    private CompletableFuture<Void> send(final HttpClientRequest req, final Request body) {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        req.endHandler(ignored -> {
            LOG.debug("HTTP backend connection ended");
            future.complete(null);
        }).exceptionHandler(ex -> {
            LOG.warn("HTTP backend exception", ex);
            future.completeExceptionally(ex);
        }).handler(response ->
                response.exceptionHandler(ex -> LOG.warn("HTTP backend response exception", ex))
                        .bodyHandler(buf -> handleInternal(buf.toString(StandardCharsets.UTF_8)))
        );

        switch (req.method()) {
            case POST:
                // TODO
                req.end(body.toString());
                break;
            default:
                //            GET:
                //            OPTIONS:
                //            HEAD:
                //            PUT:
                //            DELETE:
                //            TRACE:
                //            CONNECT:
                //            PATCH:
                //            OTHER:
                req.end();
                break;
        }
        LOG.debug("Sent message to HTTP endpoint {}: {}", req.absoluteURI(), body);
        return future;
    }

    private CompletableFuture<HttpClientRequest> request(final Request req) {
        switch (req.request()) {
            case SERVER_INFO:
                return Futures.completed(http.getRequest("info"));
            case CREATE_SESSION:
                return Futures.completed(http.postRequest(""));
            case DESTROY_SESSION: // fall through
            case ATTACH_PLUGIN: {
                final OptionalLong sessionId = req.sessionId();
                if (sessionId.isPresent()) {
                    return Futures.completed(http.postRequest("" + sessionId.getAsLong()));
                } else {
                    return Futures.illegalArgument("Missing [sessionId] in " + req);
                }
            }
            case DETACH_PLUGIN: // fall through
            case HANGUP_PLUGIN: // fall through
            case PLUGIN_MESSAGE: // fall through
            case TRICKLE: {
                final OptionalLong sessionId = req.sessionId();
                final OptionalLong pluginId = req.pluginId();
                if (sessionId.isPresent() && pluginId.isPresent()) {
                    return Futures.completed(http.postRequest(sessionId.getAsLong() + "/" + pluginId.getAsLong()));
                } else {
                    final StringBuilder sb = new StringBuilder("Missing [");
                    if (!sessionId.isPresent()) sb.append("sessionId ");
                    if (!pluginId.isPresent()) sb.append("pluginId ");
                    sb.append("] in ").append(req);
                    return Futures.illegalArgument(sb.toString());
                }
            }
            default:
                return Futures.illegalArgument("Unknown request type \"" + req.request() + "\" in " + req);
        }

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

    private void handleInternal(final String res) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Receive remote HTTP response: ");
            Splitter.on('\n').splitToList(res).forEach(line -> LOG.debug(" => {}", line));
        }

        final JsonObject json;
        try {
            json = Json.createReader(new StringReader(res))
                    .readObject();
        } catch (JsonException ex) {
            exHandler.accept(ex);
            return;
        }

        handlers.forEach(c -> c.accept(json));
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

    private void updateBackend(@Nullable final HttpClient client, final URI uri) {
        LOG.debug("HttpTransport backend switched to {} => {}", client, uri);
        if (null == client) http = HttpTransportHelper.noop();
        else http = HttpTransportHelper.std(client, uri.getPath());
    }
}
