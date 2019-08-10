package im.wangbo.bj58.janus.schema.vertx.http;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import im.wangbo.bj58.janus.schema.event.MessageReceived;
import im.wangbo.bj58.janus.schema.event.MessageSent;
import im.wangbo.bj58.janus.schema.event.SessionCreated;
import im.wangbo.bj58.janus.schema.event.SessionDestroyed;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.TransactionId;
import im.wangbo.bj58.janus.schema.transport.Transport;
import im.wangbo.bj58.janus.schema.vertx.event.AbstractEventTypeMeta;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.json.JsonObject;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class HttpTransport implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(HttpTransport.class);

    private static final int DEFAULT_HTTP_PORT = 80;
    private static final int DEFAULT_HTTPS_PORT = 443;

    private final Vertx vertx;
    private final long pollIntervalInMillis = TimeUnit.SECONDS.toMillis(2L);

    private HttpTransportHelper http = HttpTransportHelper.noop();
    private final EventBusHelper eventBus;

    private List<Consumer<JsonObject>> handlers = Collections.emptyList();
    private Consumer<Throwable> exHandler = ex -> LOG.error("HTTP backend exception", ex);
    private final ConcurrentMap<Long, Long> sessionIdMappedPollings = Maps.newConcurrentMap();

    static HttpTransport create() {
        return create(Vertx.vertx());
    }

    public static HttpTransport create(final Vertx vertx) {
        return new HttpTransport(vertx);
    }

    private HttpTransport(final Vertx vertx) {
        this.vertx = vertx;
        this.eventBus = new EventBusHelper(vertx.eventBus());
    }

    @Override
    public boolean accepts(final URI uri) {
        final String schema = uri.getScheme();
        return "http".equalsIgnoreCase(schema) || "https".equalsIgnoreCase(schema);
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        final String schema = uri.getScheme();

        final HttpClientOptions options = new HttpClientOptions()
            .setDefaultHost(uri.getHost());
        if ("http".equalsIgnoreCase(schema)) {
            options.setSsl(false).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTP_PORT : uri.getPort());
        } else if ("https".equalsIgnoreCase(schema)) {
            options.setSsl(true).setTrustAll(true).setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTPS_PORT : uri.getPort());
        } else {
            return Futures.illegalArgument("Unsupported schema \"" + schema + "\" by HttpTransport");
        }

        // Connect to HTTP endpoint
        final HttpClient httpClient = vertx.createHttpClient(options);
        updateBackend(vertx, httpClient, uri);

        vertx.eventBus().<SessionCreated>consumer(
            AbstractEventTypeMeta.create(SessionCreated.class).address(),
            msg -> onSessionCreated(msg.body()));
        vertx.eventBus().<SessionDestroyed>consumer(
            AbstractEventTypeMeta.create(SessionDestroyed.class).address(),
            msg -> onSessionDestroyed(msg.body()));
        vertx.eventBus().<MessageSent>consumer(
            AbstractEventTypeMeta.create(MessageSent.class).address(),
            msg -> onRequestSent(msg.body()));
        vertx.eventBus().<MessageReceived>consumer(
            AbstractEventTypeMeta.create(MessageReceived.class).address(),
            msg -> onResponseRecv(msg.body()));

        return Futures.completed();
    }

    @Override
    public CompletableFuture<Void> close() {
        try {
            http.close();
            return Futures.completed();
        } catch (Exception ex) {
            return Futures.failed(ex);
        }
    }

    @Override
    public CompletableFuture<Void> send(final Request msg) {
        try {
            final HttpRequesting requesting = HttpRequesting.create(msg, http, eventBus);
            return requesting.sendRequest(msg, this::onResponse);
        } catch (Exception ex) {
            return Futures.failed(ex);
        }
    }

    @Override
    public synchronized Transport handler(final Consumer<JsonObject> handler) {
        handlers = ImmutableList.<Consumer<JsonObject>>builder()
            .addAll(handlers)
            .add(exceptionSafe(handler))
            .build();
        return this;
    }

    @Override
    public Transport exceptionHandler(final Consumer<Throwable> handler) {
        this.exHandler = handler;
        return this;
    }

    private Consumer<JsonObject> exceptionSafe(final Consumer<JsonObject> c) {
        return json -> {
            try {
                c.accept(json);
            } catch (Throwable ex) {
                exHandler.accept(ex);
            }
        };
    }

    private void updateBackend(final Vertx vertx, final HttpClient client, final URI uri) {
        LOG.debug("HttpTransport backend switched to {} => {}", client, uri);
        http = HttpTransportHelper.std(vertx, client, uri.getPath(), this::onResponse);
    }

    // Either json or ex would be null but not both.
    private void onResponse(@Nullable final JsonObject json, @Nullable final Throwable ex) {
        if (null != ex) {
            exHandler.accept(ex);
        } else {
            handlers.forEach(handler -> handler.accept(json));
        }
    }

    private void onSessionCreated(final SessionCreated msg) {
        LOG.debug("Session {} created", msg.sessionId());

        final long timerId = vertx.setPeriodic(pollIntervalInMillis, ignored -> {
            final HttpRequesting requesting = new HttpRequesting.LongPollHttpRequesting(http, eventBus, msg.sessionId());
            requesting.sendRequest(
                Request.builder()
                    .type(Request.Type.NOT_PRESENT)
                    .transaction(TransactionId.of("Not needed"))
                    .build(),
                this::onResponse);
        });

        final Long existedTimerId = sessionIdMappedPollings.putIfAbsent(msg.sessionId(), timerId);
        if (null != existedTimerId) {
            vertx.cancelTimer(existedTimerId);
        }
    }

    private void onSessionDestroyed(final SessionDestroyed msg) {
        LOG.debug("Session {} destroyed", msg.sessionId());

        final Long timerId = sessionIdMappedPollings.remove(msg.sessionId());
        if (null != timerId) {
            vertx.cancelTimer(timerId);
        }
    }

    // For log output
    private void onRequestSent(final MessageSent msg) {
        LOG.debug("Message sent: {}", msg.message());
    }

    // For log output
    private void onResponseRecv(final MessageReceived msg) {
        LOG.debug("Message recv: {}", msg.message());
    }
}
