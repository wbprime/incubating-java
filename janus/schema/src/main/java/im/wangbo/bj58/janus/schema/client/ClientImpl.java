package im.wangbo.bj58.janus.schema.client;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import im.wangbo.bj58.janus.schema.GlobalRequest;
import im.wangbo.bj58.janus.schema.PluginHandleId;
import im.wangbo.bj58.janus.schema.PluginHandleRequest;
import im.wangbo.bj58.janus.schema.RequestMethod;
import im.wangbo.bj58.janus.schema.ServerInfo;
import im.wangbo.bj58.janus.schema.SessionId;
import im.wangbo.bj58.janus.schema.SessionRequest;
import im.wangbo.bj58.janus.schema.TransactionId;
import im.wangbo.bj58.janus.schema.transport.Transport;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class ClientImpl implements Client {
    private final URI uri;

    private final Transport transport;

    ClientImpl(final URI uri, final Transport transport) {
        this.uri = uri;
        this.transport = transport;
    }

    @Override
    public CompletableFuture<Void> connect() {
        return transport.connect(uri);
    }

    @Override
    public CompletableFuture<Void> close() {
        return transport.close();
    }

    @Override
    public CompletableFuture<ServerInfo> info() {
        final CompletableFuture<Void> sent = transport.send(
                Transport.RequestMessage.builder()
                        .request(RequestMethod.SERVER_INFO)
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
