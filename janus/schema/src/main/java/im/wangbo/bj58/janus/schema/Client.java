package im.wangbo.bj58.janus.schema;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Client {
    static Client create(final URI uri) {
        return new ClientImpl(uri);
    }

    CompletableFuture<Void> connect();
    CompletableFuture<Void> close();

    CompletableFuture<ServerInfo> info();

    CompletableFuture<SessionId> createSession();
    CompletableFuture<Void> destroySession(final SessionId session);

    CompletableFuture<Void> keepAlive(final SessionId session);
    CompletableFuture<PluginHandleId> attachPlugin(final SessionId session, final String plugin);
    CompletableFuture<Void> detachPlugin(final PluginHandleId handle);

    /**
     * Send "trickle" message with given candidates.
     *
     * If {@code candidates} is empty, send "trickle" with candidates sending completed;
     * otherwise send all candidates via "trickle" message.
     *
     * @param handle plugin handle
     * @param candidates candidates
     * @return future
     */
    CompletableFuture<Void> trickle(
            final PluginHandleId handle, final List<Candidate> candidates
    );

    <T> CompletableFuture<Void> request(
            final PluginHandleId handle, final T body, final Class<T> clz
    );
    <T> CompletableFuture<Void> request(
            final PluginHandleId handle, final T body, final Jsep jsep, final Class<T> clz
    );
}
