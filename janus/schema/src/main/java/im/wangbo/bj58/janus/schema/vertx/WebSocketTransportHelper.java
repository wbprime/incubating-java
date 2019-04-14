package im.wangbo.bj58.janus.schema.vertx;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
/*
interface WebSocketTransportHelper {
    default void close() {
        throw new IllegalStateException("Not Connected to a valid WebSocket endpoint");
    }

    default void send(final String msg) {
        throw new IllegalStateException("Not Connected to a valid WebSocket endpoint");
    }

    static NoopHelper noop() {
        return NoopHelper.INSTANCE;
    }

    static StdHelper std(final WebSocket ws) {
        return new StdHelper(ws);
    }

    class NoopHelper implements WebSocketTransportHelper {
        private static final NoopHelper INSTANCE = new NoopHelper();

        @Override
        public String toString() {
            return "NoopWeSocketHelper {}";
        }
    }

    class StdHelper implements WebSocketTransportHelper {
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
            return "StdWebSocketHelper {}";
        }
    }
}

 */
