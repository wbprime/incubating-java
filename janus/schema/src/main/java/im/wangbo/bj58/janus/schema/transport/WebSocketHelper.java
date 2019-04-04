package im.wangbo.bj58.janus.schema.transport;

import io.vertx.core.http.WebSocket;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
interface WebSocketHelper {
    default void close() {
        /* Default do nothing */
    }

    default void send(final String msg) {
        /* Default do nothing */
    }

    static NoopHelper noop() {
        return NoopHelper.INSTANCE;
    }

    static StdHelper std(final WebSocket ws) {
        return new StdHelper(ws);
    }

    class NoopHelper implements WebSocketHelper {
        private static final NoopHelper INSTANCE = new NoopHelper();

        @Override
        public String toString() {
            return "NoopHelper {}";
        }
    }

    class StdHelper implements WebSocketHelper {
        private final WebSocket ws;

        private StdHelper(final WebSocket ws) {
            this.ws = ws;
        }

        @Override
        public void close() {
            ws.close();
        }

        @Override
        public void send(final String msg) {
            ws.writeTextMessage(msg);
        }

        @Override
        public String toString() {
            return "StdHelper {}";
        }
    }
}
