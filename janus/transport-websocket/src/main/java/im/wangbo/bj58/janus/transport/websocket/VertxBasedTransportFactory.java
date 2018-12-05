package im.wangbo.bj58.janus.transport.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.Transport;
import im.wangbo.bj58.janus.transport.TransportFactory;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.WebsocketVersion;
import io.vertx.reactivex.core.MultiMap;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.HttpClient;
import io.vertx.reactivex.core.http.WebSocket;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class VertxBasedTransportFactory implements TransportFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxBasedTransportFactory.class);

    @Override
    public CompletableFuture<Transport> connect(final URI uri, final ResponseHandler messageHandler) {
        final StdMessageHandler stdMessageHandler = new StdMessageHandler(messageHandler);

        final Vertx vertx = Vertx.vertx();
        final HttpClient httpClient = vertx.createHttpClient();

        final RequestOptions opts = new RequestOptions()
                .setHost(uri.getHost())
                .setPort(uri.getPort())
                .setURI(uri.getPath());

        final CompletableFuture<WebSocket> connected = new CompletableFuture<>();
        httpClient.websocket(
                opts, MultiMap.caseInsensitiveMultiMap(), WebsocketVersion.V13, "janus-protocol",
                ws -> {
                    ws.textMessageHandler(stdMessageHandler::accept);
                    connected.complete(ws);
                },
                connected::completeExceptionally
        );

        LOGGER.debug("Connecting requested to \"{}\"", uri);

        return connected.thenApply(VertxBasedTransport::new);
    }
}
