package im.wangbo.bj58.janus.schema.transport;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonString;

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
    public CompletableFuture<Void> request(final JsonObject request) {
        final JsonString reqTypeJson = request.getJsonString(Constants.REQ_FIELD_REQUEST_TYPE);
        final String reqType = null != reqTypeJson ? reqTypeJson.getString() : "";
        if (Strings.isNullOrEmpty(reqType)) {
            return Futures.illegalArgument("Missing \"" + Constants.REQ_FIELD_REQUEST_TYPE + "\" field: " + request);
        }

        final Long sessionId = Constants.sessionId(request, )

        final HttpClientRequest httpRequest = request(reqType);
        if (null == httpRequest) {
            return Futures.illegalArgument(
                    "Unsupported value \"%s\" for \"%s\" field: %s",
                    reqType, Constants.REQ_FIELD_REQUEST_TYPE, request
            );
        }

        final CompletableFuture<Void> future = new CompletableFuture<>();
        httpRequest.endHandler(ignored -> {
            LOG.debug("HTTP backend connection ended");
            future.complete(null);
        })
                .exceptionHandler(ex -> {
                    LOG.warn("HTTP backend exception", ex);
                    future.completeExceptionally(ex);
                })
                .handler(response ->
                        response.exceptionHandler(ex -> LOG.warn("HTTP backend response exception", ex))
                                .bodyHandler(buf -> handleInternal(buf.toString(StandardCharsets.UTF_8)))
                );

        switch (httpRequest.method()) {
            case POST:
                httpRequest.end(request.toString());
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
                httpRequest.end();
                break;
        }

        LOG.debug("Sent message to HTTP endpoint {}: {}", httpRequest.absoluteURI(), request);
        return future;
    }

    @Nullable
    private HttpClientRequest request(final String type, @Nullable final String sessionId, @Nullable final String pluginId) {
        switch (type) {
            case Constants.REQ_REQ_TP_SERVER_INFO:
                return http.get("info");
            case Constants.REQ_REQ_TP_CREATE_SESSION:
                return http.post("");
            case Constants.REQ_REQ_TP_DESTROY_SESSION: // fall through
            case Constants.REQ_REQ_TP_ATTACH_PLUGIN:
                return http.post(sessionId);
            case Constants.REQ_REQ_TP_DETACH_PLUGIN: // fall through
            case Constants.REQ_REQ_TP_HANGUP_PLUGIN: // fall through
            case Constants.REQ_REQ_TP_PLUGIN_MESSAGE: // fall through
            case Constants.REQ_REQ_TP_TRICKLE:
                return http.post(sessionId + "/" + pluginId);
            default:
                return null;
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
