package im.wangbo.bj58.janus.schema.client;

import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.Transport;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Client {
    static CompletableFuture<Client> connect(final URI uri, final Transport transport) {
        return transport.connect(uri).thenApply(ignored -> new StdClient(transport));
    }

    CompletableFuture<Void> close();

    CompletableFuture<ServerInfo> serverInfo();

    CompletableFuture<SessionId> createSession();

    CompletableFuture<Void> destroySession(final SessionId session);

    CompletableFuture<Void> keepAlive(final SessionId session);

    CompletableFuture<PluginId> attachPlugin(final SessionId session, final String plugin);

    CompletableFuture<Void> detachPlugin(final SessionId session, final PluginId handle);

    CompletableFuture<Void> request(final GlobalRequest request);

    CompletableFuture<Void> request(final SessionRequest request);

    CompletableFuture<Void> request(final PluginRequest request);
}
