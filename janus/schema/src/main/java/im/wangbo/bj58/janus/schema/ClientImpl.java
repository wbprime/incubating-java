package im.wangbo.bj58.janus.schema;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class ClientImpl implements Client {
    private final URI uri;

    private final Transport transport;

    ClientImpl(final URI uri) {
        this.uri = uri;
        this.transport = Transport.noop();
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
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        final CompletableFuture<Void> sent = transport.request(builder.build());

        final CompletableFuture<ServerInfo> future = new CompletableFuture<>();

        return future;
    }

    @Override
    public CompletableFuture<SessionId> createSession() {
        Jsonb
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
    public CompletableFuture<Void> detachPlugin(PluginHandleId handle) {
        return null;
    }

    @Override
    public CompletableFuture<Void> trickle(PluginHandleId handle, List<Candidate> candidates) {
        return null;
    }

    @Override
    public <T> CompletableFuture<Void> request(
            final PluginHandleId handle, final T body, final Class<T> clz
    ) {
        return null;
    }

    @Override
    public <T> CompletableFuture<Void> request(
            final PluginHandleId handle, final T body, final Jsep jsep, final Class<T> clz
    ) {
        return null;
    }
}
