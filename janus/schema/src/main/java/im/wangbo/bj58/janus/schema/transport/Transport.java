package im.wangbo.bj58.janus.schema.transport;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Transport {
    default String name() {
        return "Transport";
    }

    default boolean accepts(final URI uri) {
        return false;
    }

    CompletableFuture<Void> connect(final URI uri);

    CompletableFuture<Void> close();

    CompletableFuture<Void> send(final TransportRequest msg);

    Transport handler(final Consumer<JsonObject> handler);

    Transport exceptionHandler(final Consumer<Throwable> handler);

//    static Transport noop() {
//        return NoopTransport.instance();
//    }
//
//    static Transport websocket() {
//        return new WebSocketTransport();
//    }

}
