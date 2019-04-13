package im.wangbo.bj58.janus.schema.transport;

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
    default HttpClientRequest get(final String subPath) {
        throw new IllegalStateException("Not Connected to a valid HTTP endpoint");
    }

    /**
     * @param subPath sub path prefixed with "/"
     * @return request
     */
    default HttpClientRequest post(final String subPath) {
        throw new IllegalStateException("Not Connected to a valid HTTP endpoint");
    }

    static NoopHelper noop() {
        return NoopHelper.INSTANCE;
    }

    static StdHelper std(final HttpClient client, final String rootPath) {
        return new StdHelper(client, rootPath);
    }

    class NoopHelper implements HttpTransportHelper {
        private static final NoopHelper INSTANCE = new NoopHelper();

        @Override
        public String toString() {
            return "NoopHttpHelper {}";
        }
    }

    class StdHelper implements HttpTransportHelper {
        private final HttpClient http;
        private final String rootPath;

        private StdHelper(final HttpClient client, final String root) {
            this.http = client;
            this.rootPath = root.startsWith("/") ? root : "/" + root;
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
        public HttpClientRequest get(final String subPath) {
            return http.get(concatPath(subPath));
        }

        @Override
        public HttpClientRequest post(final String subPath) {
            return http.post(concatPath(subPath));
        }

        @Override
        public String toString() {
            return "StdHttpHelper {}";
        }
    }
}
