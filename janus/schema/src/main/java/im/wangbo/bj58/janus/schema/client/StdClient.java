package im.wangbo.bj58.janus.schema.client;

import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;
import im.wangbo.bj58.janus.schema.transport.Transport;

import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class StdClient implements Client {
    private final Transport transport;

    StdClient(final Transport transport) {
        this.transport = transport;
    }

    @Override
    public CompletableFuture<Void> close() {
        return transport.close();
    }

    @Override
    public CompletableFuture<ServerInfo> serverInfo() {
        final CompletableFuture<Void> sent = transport.send(
            Request.serverInfoMessageBuilder()
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
    public CompletableFuture<PluginId> attachPlugin(SessionId session, String plugin) {
        return null;
    }

    @Override
    public CompletableFuture<Void> detachPlugin(final SessionId session, PluginId handle) {
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
    public CompletableFuture<Void> request(PluginRequest request) {
        return null;
    }
}
