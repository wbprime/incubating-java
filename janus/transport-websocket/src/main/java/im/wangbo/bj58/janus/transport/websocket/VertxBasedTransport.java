package im.wangbo.bj58.janus.transport.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.json.Json;

import im.wangbo.bj58.janus.transport.GlobalRequest;
import im.wangbo.bj58.janus.transport.PluginHandleRequest;
import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.SessionRequest;
import im.wangbo.bj58.janus.transport.Transport;
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
public class VertxBasedTransport implements Transport {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxBasedTransport.class);

    private volatile Optional<WebSocket> curWebSocket;

    private VertxBasedTransport() {
        this.curWebSocket = Optional.empty();
    }

    public static Transport create() {
        return new VertxBasedTransport();
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri, final ResponseHandler messageHandler) {
        final CompletableFuture<Void> promise = new CompletableFuture<>();

        final StdMessageHandler stdMessageHandler = new StdMessageHandler(messageHandler);

        final Vertx vertx = Vertx.vertx();
        final HttpClient httpClient = vertx.createHttpClient();

        final RequestOptions opts = new RequestOptions()
                .setHost(uri.getHost())
                .setPort(uri.getPort())
                .setURI(uri.getPath());
        httpClient.websocket(
                opts, MultiMap.caseInsensitiveMultiMap(), WebsocketVersion.V13, "janus-protocol",
                websocket -> {
                    websocket.textMessageHandler(stdMessageHandler::accept);
                    curWebSocket = Optional.of(websocket);
                    promise.complete(null);
                },
                promise::completeExceptionally
        );

        LOGGER.debug("Connecting requested to \"{}\"", uri);
        return promise;
    }

    @Override
    public CompletableFuture<Void> close() {
        curWebSocket.ifPresent(WebSocket::close);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> send(final GlobalRequest request) {
        return curWebSocket.map(
                ws -> {
                    final JanusReq req = JanusReq.builder()
                            .requestType(request.request())
                            .transactionId(request.transaction().id())
                            .build();
                    return send(ws, req);
                }
        ).orElse(notConnected());
    }

    @Override
    public CompletableFuture<Void> send(final SessionRequest request) {
        return curWebSocket.map(
                ws -> {
                    final JanusReq req = JanusReq.builder()
                            .requestType(request.request())
                            .transactionId(request.transaction().id())
                            .sessionId(request.session().id())
                            .body(request.message())
                            .build();
                    return send(ws, req);
                }
        ).orElse(notConnected());
    }

    @Override
    public CompletableFuture<Void> send(final PluginHandleRequest request) {
        return curWebSocket.map(
                ws -> {
                    final JanusReq req = JanusReq.builder()
                            .requestType(request.request())
                            .transactionId(request.transaction().id())
                            .sessionId(request.pluginHandle().session().id())
                            .pluginHandleId(request.pluginHandle().id())
                            .body(request.data())
//                            .jsep(request.jsep())
//                            .candidates(request.candidates())
                            .build();
                    return send(ws, req);
                }
        ).orElse(notConnected());
    }

    private CompletableFuture<Void> send(final WebSocket webSocket, final JanusReq req) {
        final StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(req.toJson());
        webSocket.writeTextMessage(writer.toString());
        return CompletableFuture.completedFuture(null);
    }

    private CompletableFuture<Void> notConnected() {
        final CompletableFuture<Void> promise = new CompletableFuture<>();
        promise.completeExceptionally(new RuntimeException("Not connected"));
        return promise;
    }
}
