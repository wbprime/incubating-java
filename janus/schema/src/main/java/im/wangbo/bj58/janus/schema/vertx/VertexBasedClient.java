package im.wangbo.bj58.janus.schema.vertx;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import im.wangbo.bj58.janus.schema.GlobalRequest;
import im.wangbo.bj58.janus.schema.PluginHandleRequest;
import im.wangbo.bj58.janus.schema.ServerInfo;
import im.wangbo.bj58.janus.schema.SessionRequest;
import im.wangbo.bj58.janus.schema.client.Client;
import im.wangbo.bj58.janus.schema.event.EventHandlerRegistry;
import im.wangbo.bj58.janus.schema.transport.PluginHandleId;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;
import im.wangbo.bj58.janus.schema.transport.Transport;
import im.wangbo.bj58.janus.schema.transport.TransportRequest;
import im.wangbo.bj58.janus.schema.vertx.http.HttpTransport;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class VertexBasedClient implements Client {
    private final EventBus eventBus;
    private final Transport transport;

    VertexBasedClient() {
        final Vertx vertx = Vertx.vertx();

        this.eventBus = vertx.eventBus();
        this.transport = HttpTransport.create(vertx);
    }

    @Override
    public EventHandlerRegistry eventHandlerRegistry() {
        return new EventHandlerRegistry() { };
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri, final Transport transport) {
        return transport.connect(uri).whenComplete((re, ex) -> {/* TODO handler event handlers */});
    }

    @Override
    public CompletableFuture<Void> close() {
        return transport.close();
    }

    @Override
    public CompletableFuture<ServerInfo> info() {
        final CompletableFuture<Void> sent = transport.send(
                TransportRequest.serverInfoMessageBuilder()
                        .transaction(TransactionId.of("wbprime" + System.currentTimeMillis()))
                        .build()
        );

        final CompletableFuture<ServerInfo> future = new CompletableFuture<>();

        return future;
    }

    @Override
    public CompletableFuture<SessionId> createSession() {
        return null;
    }

    @Override
    public CompletableFuture<Void> destroySession(SessionId session) {
        return null;
    }

    @Override
    public CompletableFuture<Void> keepAlive(SessionId session) {
        return null;
    }

    @Override
    public CompletableFuture<PluginHandleId> attachPlugin(SessionId session, String plugin) {
        return null;
    }

    @Override
    public CompletableFuture<Void> detachPlugin(final SessionId session, PluginHandleId handle) {
        return null;
    }

    @Override
    public CompletableFuture<Void> request(GlobalRequest request) {
        return null;
    }

    @Override
    public CompletableFuture<Void> request(SessionRequest request) {
        return null;
    }

    @Override
    public CompletableFuture<Void> request(PluginHandleRequest request) {
        return null;
    }
}