package im.wangbo.bj58.janus.schema.vertx.http;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import im.wangbo.bj58.janus.schema.event.MessageReceived;
import im.wangbo.bj58.janus.schema.event.MessageSent;
import im.wangbo.bj58.janus.schema.event.SessionCreated;
import im.wangbo.bj58.janus.schema.event.SessionDestroyed;
import im.wangbo.bj58.janus.schema.transport.Request;
import im.wangbo.bj58.janus.schema.transport.TransactionId;
import im.wangbo.bj58.janus.schema.transport.Transport;
import im.wangbo.bj58.janus.schema.utils.Events;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.json.JsonObject;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    private volatile Optional<HttpClient> httpClientOpt = Optional.empty();
    private volatile Optional<EventBus> eventBusOpt = Optional.empty();

    private List<Consumer<JsonObject>> handlers = Lists.newArrayList();
    private Consumer<Throwable> exHandler = ex -> LOG.error("HTTP backend exception", ex);

    private final ConcurrentMap<Long, Long> sessionIdMappedPollings = Maps.newConcurrentMap();

    private String rootPath;

    static HttpTransport create() {
        return create(Vertx.vertx());
    }

    public static HttpTransport create(final Vertx vertx) {
        return new HttpTransport(vertx);
    }

    private HttpTransport(final Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public String name() {
        return "vert.x-based-http";
    }

    @Override
    public boolean accepts(final URI uri) {
        final String schema = uri.getScheme();
        return "http".equalsIgnoreCase(schema) || "https".equalsIgnoreCase(schema);
    }

    @Override
    public CompletableFuture<Void> connect(final URI uri) {
        return CompletableFuture.runAsync(() -> {
            final String schema = uri.getScheme();

            final HttpClientOptions options = new HttpClientOptions().setDefaultHost(uri.getHost());
            if ("http".equalsIgnoreCase(schema)) {
                options.setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTP_PORT : uri.getPort())
                    .setSsl(false);
            } else if ("https".equalsIgnoreCase(schema)) {
                options.setDefaultPort(uri.getPort() == -1 ? DEFAULT_HTTPS_PORT : uri.getPort())
                    .setSsl(true).setTrustAll(true);
            } else {
                throw new IllegalArgumentException("Unsupported schema \"" + schema + "\" by HttpTransport");
            }

            final HttpClient httpClient = vertx.createHttpClient(options);
            final EventBus eventBus = vertx.eventBus();

            eventBus.registerCodec(new JsonObjCodec());
            eventBus.registerCodec(new JsonArrCodec());
            eventBus.registerCodec(new SessionCreatedCodec());
            eventBus.registerCodec(new SessionDestroyedCodec());
            eventBus.registerCodec(new MessageSentCodec());
            eventBus.registerCodec(new MessageReceivedCodec());

            eventBus.<SessionCreated>consumer(Events.address(SessionCreated.class),
                msg -> onSessionCreated(msg.body()));
            eventBus.<SessionDestroyed>consumer(Events.address(SessionDestroyed.class),
                msg -> onSessionDestroyed(msg.body()));
            eventBus.<MessageSent>consumer(Events.address(MessageSent.class),
                msg -> logMessage(msg.body()));
            eventBus.<MessageReceived>consumer(Events.address(MessageReceived.class),
                msg -> logMessage(msg.body()));

            httpClientOpt = Optional.of(httpClient);
            eventBusOpt = Optional.of(eventBus);
            rootPath = uri.getPath();
        });
    }

    @Override
    public CompletableFuture<Void> close() {
        return CompletableFuture.runAsync(() -> httpClientOpt.ifPresent(HttpClient::close));
    }

    @Override
    public CompletableFuture<Void> send(final Request msg) {
        return CompletableFuture.supplyAsync(
            () -> HttpRequesting.create(httpClientOpt, eventBusOpt, rootPath, msg)
        ).thenAccept(requesting -> requesting.sendRequest(msg, this::onResponse));
    }

    @Override
    public Transport handler(final Consumer<JsonObject> handler) {
        this.handlers.add(exceptionSafe(handler));
        return this;
    }

    @Override
    public synchronized Transport exceptionHandler(final Consumer<Throwable> handler) {
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
            final HttpRequesting requesting = HttpRequesting.longPolling(
                httpClientOpt, eventBusOpt, rootPath, msg.sessionId());

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
    private <T> void logMessage(final T msg) {
        LOG.debug("On message: {}", msg);
    }
}
