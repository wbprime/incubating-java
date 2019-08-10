package im.wangbo.bj58.janus.schema.vertx.http;

import im.wangbo.bj58.janus.schema.event.MessageReceived;
import im.wangbo.bj58.janus.schema.event.MessageSent;
import im.wangbo.bj58.janus.schema.event.SessionCreated;
import im.wangbo.bj58.janus.schema.event.SessionDestroyed;
import im.wangbo.bj58.janus.schema.transport.Request;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
abstract class HttpRequesting {
    private final HttpClient httpClient;
    private final EventBus eventBus;

    private final String rootPath;

    HttpRequesting(final HttpClient http, final EventBus eventBus, final String path) {
        this.httpClient = http;
        this.eventBus = eventBus;

        String str = path;
        while (str.endsWith("/")) str = str.substring(0, str.length() - 1);

        this.rootPath = str + "/";
    }

    static HttpRequesting create(
        final Optional<HttpClient> httpClientOpt, final Optional<EventBus> eventBusOpt,
        final String rootPath, final Request msg) {

        if (!(httpClientOpt.isPresent() && eventBusOpt.isPresent())) {
            return new NoopHttpRequesting();
        }

        final HttpClient httpClient = httpClientOpt.get();
        final EventBus eventBus = eventBusOpt.get();

        final OptionalLong sessionId = msg.sessionId()
            .map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());
        final OptionalLong pluginId = msg.pluginId()
            .map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());

        switch (msg.type()) {
            case SERVER_INFO:
                return new ServerInfoHttpRequesting(httpClient, eventBus, rootPath);
            case CREATE_SESSION:
                return new CreateSessionHttpRequesting(httpClient, eventBus, rootPath);
            case DESTROY_SESSION: // fall through
            case ATTACH_PLUGIN: {
                if (sessionId.isPresent()) {
                    return new SessionBasedPostHttpRequesting(httpClient, eventBus,
                        rootPath, sessionId.getAsLong());
                } else {
                    throw new IllegalArgumentException("Missing [sessionId] in " + msg);
                }
            }
            case DETACH_PLUGIN: // fall through
            case HANGUP_PLUGIN: // fall through
            case SEND_MESSAGE: // fall through
            case TRICKLE: {
                if (sessionId.isPresent() && pluginId.isPresent()) {
                    return new PluginPostHttpRequesting(httpClient, eventBus,
                        rootPath, sessionId.getAsLong(), pluginId.getAsLong());
                } else {
                    final StringBuilder sb = new StringBuilder("Missing [");
                    if (!sessionId.isPresent()) sb.append("sessionId ");
                    if (!pluginId.isPresent()) sb.append("pluginId ");
                    sb.append("] in ").append(msg);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            default:
                if (sessionId.isPresent()) {
                    if (pluginId.isPresent()) {
                        return new PluginPostHttpRequesting(httpClient, eventBus,
                            rootPath, sessionId.getAsLong(), pluginId.getAsLong());
                    } else {
                        return new SessionBasedPostHttpRequesting(httpClient, eventBus,
                            rootPath, sessionId.getAsLong());
                    }
                } else {
                    return new GlobalBasedHttpRequesting(httpClient, eventBus, rootPath);
                }
        }
    }

    final HttpClient httpClient() {
        return httpClient;
    }

    final EventBus eventBus() {
        return eventBus;
    }

    private void handleResponseAsJson(
        final Buffer buf, final BiConsumer<JsonObject, Throwable> handler
    ) {
        final String res = buf.toString(StandardCharsets.UTF_8);

        final JsonValue json;
        try {
            json = Json.createReader(new StringReader(res)).readValue();
        } catch (JsonException ex) {
            handler.accept(null, ex);
            return;
        }

        switch (json.getValueType()) {
            case ARRAY: {
                ((JsonArray) json).forEach(
                    v -> {
                        if (v.getValueType() == JsonValue.ValueType.OBJECT) {
                            final JsonObject o = (JsonObject) v;
                            notifyResponseRecv(o);
                            handler.accept(o, null);
                        } else {
                            handler.accept(null, new IllegalArgumentException(
                                "Expected only JSON object in top level array but not: " + res));
                        }
                    }
                );
            }
            break;
            case OBJECT: {
                final JsonObject o = (JsonObject) json;
                notifyResponseRecv(o);
                handler.accept(o, null);
            }
            break;
            default:
                // STRING:
                // NUMBER:
                // TRUE:
                // FALSE:
                // NULL:
                handler.accept(null, new IllegalArgumentException("Expected JSON object or array but not: " + res));
                break;
        }
    }

    CompletableFuture<Void> sendRequest(
        final Request msg, final BiConsumer<JsonObject, Throwable> responseHandler
    ) {
        // Response
        final CompletableFuture<Void> requestFuture = new CompletableFuture<>();

        final HttpClientRequest request = buildHttpRequest(rootPath)
            .endHandler(ignored -> requestFuture.complete(null))
            .exceptionHandler(requestFuture::completeExceptionally)
            .handler(res -> res.exceptionHandler(ex -> responseHandler.accept(null, ex))
                .bodyHandler(buf -> handleResponseAsJson(buf, responseHandler))
            );

        final Optional<JsonObject> body = requestBody(msg);
        if (body.isPresent()) request.end(body.get().toString());
        else request.end();

        notifyRequestSent(request.method().name(), request.absoluteURI(),
            body.orElse(JsonObject.EMPTY_JSON_OBJECT));

        return requestFuture;
    }

    // Visible for inheriting
    void notifyRequestSent(final String httpMethod, final String uri, final JsonObject data) {
        Events.sendEvent(eventBus(), MessageSent.of(data), MessageSent.class);
    }

    // Visible for inheriting
    void notifyResponseRecv(final JsonObject data) {
        Events.sendEvent(eventBus(), MessageReceived.of(data), MessageReceived.class);
    }

    // Visible for inheriting
    abstract HttpClientRequest buildHttpRequest(final String rootPath);

    // Visible for inheriting
    abstract Optional<JsonObject> requestBody(final Request msg);

    static final class NoopHttpRequesting extends HttpRequesting {
        NoopHttpRequesting() {
            super(null, null, null);
        }

        @Override
        final CompletableFuture<Void> sendRequest(
            final Request msg, final BiConsumer<JsonObject, Throwable> responseHandler) {
            return Futures.illegalState("HTTP requesting illegal state without initialized");
        }

        @Override
        final HttpClientRequest buildHttpRequest(final String rootPath) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        final Optional<JsonObject> requestBody(final Request msg) {
            throw new UnsupportedOperationException("Not implemented");
        }
    }

    static abstract class GetHttpRequesting extends HttpRequesting {
        GetHttpRequesting(final HttpClient http, final EventBus eventBus, final String path) {
            super(http, eventBus, path);
        }

        @Override
        final HttpClientRequest buildHttpRequest(final String rootPath) {
            return httpClient().get(rootPath + subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Request body) {
            return Optional.empty();
        }

        abstract String subPath();
    }

    static class ServerInfoHttpRequesting extends GetHttpRequesting {
        ServerInfoHttpRequesting(final HttpClient http, final EventBus eventBus, final String path) {
            super(http, eventBus, path);
        }

        @Override
        final String subPath() {
            return "info";
        }
    }

    static class LongPollHttpRequesting extends GetHttpRequesting {
        private final long sid;
        private final int maxEvents = 10;

        LongPollHttpRequesting(
            final HttpClient http, final EventBus eventBus,
            final String path, final long sid) {
            super(http, eventBus, path);
            this.sid = sid;
        }

        @Override
        final String subPath() {
            return sid + "?maxev=" + maxEvents;
        }
    }

    static abstract class PostHttpRequesting extends HttpRequesting {
        PostHttpRequesting(final HttpClient http, final EventBus eventBus, final String path) {
            super(http, eventBus, path);
        }

        @Override
        final HttpClientRequest buildHttpRequest(final String rootPath) {
            return httpClient().post(rootPath + subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Request body) {
            final JsonObjectBuilder builder = Json.createObjectBuilder(body.root());
            builder.add(Constants.REQ_FIELD_REQUEST_TYPE, body.type().type());
            builder.add(Constants.REQ_FIELD_TRANSACTION, body.transaction().id());
            return Optional.of(builder.build());
        }

        abstract String subPath();
    }

    static class GlobalBasedHttpRequesting extends PostHttpRequesting {
        GlobalBasedHttpRequesting(
            final HttpClient http, final EventBus eventBus, final String path) {
            super(http, eventBus, path);
        }

        @Override
        final String subPath() {
            return "";
        }
    }

    static class CreateSessionHttpRequesting extends GlobalBasedHttpRequesting {
        CreateSessionHttpRequesting(
            final HttpClient http, final EventBus eventBus, final String path) {
            super(http, eventBus, path);
        }

        @Override
        void notifyResponseRecv(JsonObject data) {
            super.notifyResponseRecv(data);

            // TODO check success in data
            final OptionalLong sessionId = Constants.sessionId(data);
            sessionId.ifPresent(
                id -> Events.sendEvent(eventBus(), SessionCreated.of(id), SessionCreated.class)
            );
        }
    }

    static class SessionBasedPostHttpRequesting extends PostHttpRequesting {
        private final long sid;

        SessionBasedPostHttpRequesting(
            final HttpClient http, final EventBus eventBus, final String path, final long sid) {
            super(http, eventBus, path);
            this.sid = sid;
        }

        final long sessionId() {
            return sid;
        }

        @Override
        final String subPath() {
            return String.valueOf(sid);
        }
    }

    static class DestroySessionHttpRequesting extends SessionBasedPostHttpRequesting {
        DestroySessionHttpRequesting(
            final HttpClient http, final EventBus eventBus, final String path, final long sid) {
            super(http, eventBus, path, sid);
        }

        @Override
        void notifyResponseRecv(JsonObject data) {
            super.notifyResponseRecv(data);

            // TODO check success in data
            Events.sendEvent(
                eventBus(), SessionDestroyed.of(sessionId()), SessionDestroyed.class);
        }
    }

    static class PluginPostHttpRequesting extends PostHttpRequesting {
        private final long sid;
        private final long hid;

        PluginPostHttpRequesting(
            final HttpClient http, final EventBus eventBus, final String path,
            final long sessionId, final long pluginId) {

            super(http, eventBus, path);

            this.sid = sessionId;
            this.hid = pluginId;
        }

        @Override
        final String subPath() {
            return sid + "/" + hid;
        }
    }

    static HttpRequesting longPolling(
        final Optional<HttpClient> httpClient,
        final Optional<EventBus> eventBus,
        final String path,
        final long sessionId) {

        if (httpClient.isPresent() && eventBus.isPresent()) {
            return new LongPollHttpRequesting(httpClient.get(), eventBus.get(), path, sessionId);
        } else {
            return new NoopHttpRequesting();
        }
    }

}
