package im.wangbo.bj58.janus.schema.transport;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;

import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebsocketVersion;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class WebSocketTransport implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketTransport.class);

    private static final int DEFAULT_WS_PORT = 80;
    private static final int DEFAULT_WSS_PORT = 443;
    private static final String WS_SUB_PROTOCOL = "janus-protocol";

    private List<Consumer<JsonObject>> handlers = Collections.emptyList();
    private Consumer<Throwable> exHandler = ex -> LOG.error("WebSocket backend exception", ex);

    private WebSocketTransportHelper websocket = WebSocketTransportHelper.noop();

    static WebSocketTransport create() {
        return new WebSocketTransport();
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        final String schema = uri.getScheme();

        final RequestOptions options = new RequestOptions().setHost(uri.getHost()).setURI(uri.getPath());
        if ("ws".equalsIgnoreCase(schema)) {
            options.setSsl(false).setPort(uri.getPort() == -1 ? DEFAULT_WS_PORT : uri.getPort());
        } else if ("wss".equalsIgnoreCase(schema)) {
            options.setSsl(true).setPort(uri.getPort() == -1 ? DEFAULT_WSS_PORT : uri.getPort());
        } else {
            return Futures.illegalArgument("Unsupported schema \"" + schema + "\" by WebSocketTransport");
        }

        LOG.debug("Trying to connect Janus WebSocket server on Host: \"{}\", Port {}, SSL: {}, Path: \"{}\"",
                options.getHost(), options.getPort(), options.isSsl(), options.getURI());

        // Connect to WebSocket
        final CompletableFuture<Void> future = new CompletableFuture<>();
        Vertx.vertx().createHttpClient(new HttpClientOptions().setTrustAll(true))
                .websocket(
                        options,
                        MultiMap.caseInsensitiveMultiMap(),
                        WebsocketVersion.V13,
                        WS_SUB_PROTOCOL,
                        ws -> {
                            updateWebSocket(ws);
                            ws.closeHandler(ignored -> updateWebSocket(null))
                                    .endHandler(ignored -> LOG.debug("WebSocket backend connection ended"))
                                    .exceptionHandler(ex -> LOG.warn("WebSocket backend exception to \"{}\"", uri, ex))
                                    .textMessageHandler(this::handleInternal)
                                    .binaryMessageHandler(ignored -> LOG.warn("Received binary frame"));
                            future.complete(null);
                        }
                );
        return future;
    }

    @Override
    public CompletableFuture<Void> close() {
        try {
            websocket.close();
            return Futures.completed();
        } catch (Exception ex) {
            return Futures.failed(ex);
        }
    }

    @Override
    public CompletableFuture<Void> send(final Request request) {
        try {
            websocket.send(request.toString());
            return Futures.completed();
        } catch (Exception ex) {
            return Futures.failed(ex);
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
            LOG.debug("Receive remote WebSocket response: ");
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

    private void updateWebSocket(@Nullable final WebSocket ws) {
        LOG.debug("WebSocketTransport backend switched to {}", ws);
        if (null == ws) websocket = WebSocketTransportHelper.noop();
        else websocket = WebSocketTransportHelper.std(ws);
    }
}
