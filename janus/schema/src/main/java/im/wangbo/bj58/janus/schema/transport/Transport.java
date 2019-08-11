package im.wangbo.bj58.janus.schema.transport;

import javax.json.JsonObject;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Transport {
    static Transport noop() {
        return NoopTransport.instance();
    }

    /**
     * @param uri target uri
     * @return true if {@code uri} can be handled by this transport
     */
    default boolean accepts(final URI uri) {
        return false;
    }

    /**
     * @return name
     */
    String name();

    /**
     * @param uri target uri
     * @return connecting future
     */
    CompletableFuture<Void> connect(final URI uri);

    /**
     * @return closing future
     */
    CompletableFuture<Void> close();

    /**
     * @param msg request message
     * @return message sending future
     */
    CompletableFuture<Void> send(final Request msg);

    /**
     * Support multi result handlers.
     *
     * @param handler result handler
     * @return this
     */
    Transport handler(final Consumer<JsonObject> handler);

    /**
     * Support multi exception handlers.
     *
     * @param handler exception handler
     * @return this
     */
    Transport exceptionHandler(final Consumer<Throwable> handler);
}
