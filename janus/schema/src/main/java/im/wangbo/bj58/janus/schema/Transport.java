package im.wangbo.bj58.janus.schema;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Transport {
    CompletableFuture<Void> connect(final URI uri);

    CompletableFuture<Void> close();

    CompletableFuture<Void> request(final JsonObject request);

    static Transport noop() {
        return NoopTransport.instance();
    }
}
