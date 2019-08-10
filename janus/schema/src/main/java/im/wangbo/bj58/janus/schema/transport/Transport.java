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
    default boolean accepts(final URI uri) {
        return false;
    }

    String name();

    CompletableFuture<Void> connect(final URI uri);

    CompletableFuture<Void> close();

    CompletableFuture<Void> send(final Request msg);

    Transport handler(final Consumer<JsonObject> handler);

    Transport exceptionHandler(final Consumer<Throwable> handler);
}
