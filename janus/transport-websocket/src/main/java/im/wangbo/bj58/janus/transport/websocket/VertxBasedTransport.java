package im.wangbo.bj58.janus.transport.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.concurrent.CompletableFuture;

import javax.json.Json;

import im.wangbo.bj58.janus.transport.GlobalRequest;
import im.wangbo.bj58.janus.transport.PluginHandleRequest;
import im.wangbo.bj58.janus.transport.SessionRequest;
import im.wangbo.bj58.janus.transport.Transport;
import io.vertx.reactivex.core.http.WebSocket;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class VertxBasedTransport implements Transport {
    private static final Logger LOGGER = LoggerFactory.getLogger(VertxBasedTransport.class);

    private final WebSocket curWebSocket;

    VertxBasedTransport(WebSocket webSocket) {
        this.curWebSocket = webSocket;
    }

    @Override
    public CompletableFuture<Void> close() {
        curWebSocket.close();
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> send(final GlobalRequest request) {
        final JanusReq req = JanusReq.builder()
                .requestType(request.request())
                .transactionId(request.transaction().id())
                .build();
        return send(curWebSocket, req);
    }

    @Override
    public CompletableFuture<Void> send(final SessionRequest request) {
        final JanusReq req = JanusReq.builder()
                .requestType(request.request())
                .transactionId(request.transaction().id())
                .sessionId(request.session().id())
                .body(request.message())
                .build();
        return send(curWebSocket, req);
    }

    @Override
    public CompletableFuture<Void> send(final PluginHandleRequest request) {
        final JanusReq req = JanusReq.builder()
                .requestType(request.request())
                .transactionId(request.transaction().id())
                .sessionId(request.pluginHandle().session().id())
                .pluginHandleId(request.pluginHandle().id())
                .body(request.data())
//                            .jsep(request.jsep())
//                            .candidates(request.candidates())
                .build();
        return send(curWebSocket, req);
    }

    private CompletableFuture<Void> send(final WebSocket webSocket, final JanusReq req) {
        final StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(req.toJson());
        webSocket.writeTextMessage(writer.toString());
        return CompletableFuture.completedFuture(null);
    }
}
