package im.wangbo.bj58.janus.schema.transport;

import java.util.function.BiConsumer;

import javax.json.JsonObject;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
interface HttpTransportHelper {
    default void close() {
        throw new IllegalStateException("Not Connected to a valid HTTP endpoint");
    }

    /**
     * @param subPath sub path prefixed with "/"
     * @return request
     */
    default HttpClientRequest getRequest(final String subPath) {
        throw new IllegalStateException("Not Connected to a valid HTTP endpoint");
    }

    /**
     * @param subPath sub path prefixed with "/"
     * @return request
     */
    default HttpClientRequest postRequest(final String subPath) {
        throw new IllegalStateException("Not Connected to a valid HTTP endpoint");
    }

    static NoopHelper noop() {
        return NoopHelper.INSTANCE;
    }

    static StdHelper std(
            final Vertx vertx, final HttpClient client, final String rootPath,
            final BiConsumer<JsonObject, Throwable> handler
    ) {
        return new StdHelper(vertx, client, rootPath, handler);
    }

    class NoopHelper implements HttpTransportHelper {
        private static final NoopHelper INSTANCE = new NoopHelper();

        @Override
        public String toString() {
            return "NoopHttpHelper {}";
        }
    }

    class StdHelper implements HttpTransportHelper {
        private final Vertx vertx;
        private final HttpClient http;
        private final String rootPath;

        private final BiConsumer<JsonObject, Throwable> handler;

        StdHelper(
                final Vertx vertx, final HttpClient client, final String root,
                final BiConsumer<JsonObject, Throwable> handler
        ) {
            this.vertx = vertx;
            this.http = client;
            this.rootPath = root.startsWith("/") ? root : "/" + root;
            this.handler = handler;
        }

        @Override
        public void close() {
            http.close();
        }

        private String concatPath(final String subPath) {
            return subPath.isEmpty() ? rootPath :
                    (subPath.startsWith("/") ? rootPath + subPath : rootPath + "/" + subPath);
        }

        @Override
        public HttpClientRequest getRequest(final String subPath) {
            return http.get(concatPath(subPath));
        }

        @Override
        public HttpClientRequest postRequest(final String subPath) {
            return http.post(concatPath(subPath));
        }

        @Override
        public String toString() {
            return "StdHttpHelper {}";
        }
    }
}
