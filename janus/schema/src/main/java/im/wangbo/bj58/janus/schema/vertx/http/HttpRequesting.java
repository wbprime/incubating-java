package im.wangbo.bj58.janus.schema.vertx.http;

import im.wangbo.bj58.janus.schema.event.MessageReceived;
import im.wangbo.bj58.janus.schema.event.MessageSent;
import im.wangbo.bj58.janus.schema.event.SessionCreated;
import im.wangbo.bj58.janus.schema.event.SessionDestroyed;
import im.wangbo.bj58.janus.schema.transport.Request;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequesting.class);

    private final HttpTransportHelper http;
    private final EventBusHelper eventBus;

    HttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper) {
        this.http = helper;
        this.eventBus = eventBusHelper;
    }

    static HttpRequesting create(
        final Request msg,
        final HttpTransportHelper httpHelper,
        final EventBusHelper eventBusHelper
    ) {
        final OptionalLong sessionId = msg.sessionId().map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());
        final OptionalLong pluginId = msg.pluginId().map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());

        switch (msg.type()) {
            case SERVER_INFO:
                return new ServerInfoHttpRequesting(httpHelper, eventBusHelper);
            case CREATE_SESSION:
                return new CreateSessionHttpRequesting(httpHelper, eventBusHelper);
            case DESTROY_SESSION: // fall through
            case ATTACH_PLUGIN: {
                if (sessionId.isPresent()) {
                    return new SessionBasedPostHttpRequesting(httpHelper, eventBusHelper, sessionId.getAsLong());
                } else {
                    throw new IllegalArgumentException("Missing [sessionId] in " + msg);
                }
            }
            case DETACH_PLUGIN: // fall through
            case HANGUP_PLUGIN: // fall through
            case SEND_MESSAGE: // fall through
            case TRICKLE: {
                if (sessionId.isPresent() && pluginId.isPresent()) {
                    return new PluginPostHttpRequesting(httpHelper, eventBusHelper, sessionId.getAsLong(), pluginId.getAsLong());
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
                        return new PluginPostHttpRequesting(httpHelper, eventBusHelper, sessionId.getAsLong(), pluginId.getAsLong());
                    } else {
                        return new SessionBasedPostHttpRequesting(httpHelper, eventBusHelper, sessionId.getAsLong());
                    }
                } else {
                    return new GlobalBasedHttpRequesting(httpHelper, eventBusHelper);
                }
        }
    }

    final HttpTransportHelper httpHelper() {
        return http;
    }

    final EventBusHelper eventBus() {
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
            case ARRAY:
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

    final CompletableFuture<Void> sendRequest(
        final Request msg, final BiConsumer<JsonObject, Throwable> responseHandler
    ) {
        // Response
        final CompletableFuture<Void> requestFuture = new CompletableFuture<>();

        final HttpClientRequest request = buildHttpRequest()
            .endHandler(ignored -> requestFuture.complete(null))
            .exceptionHandler(requestFuture::completeExceptionally)
            .handler(res -> res.exceptionHandler(ex -> responseHandler.accept(null, ex))
                .bodyHandler(buf -> handleResponseAsJson(buf, responseHandler))
            );

        final Optional<JsonObject> body = requestBody(msg);
        if (body.isPresent()) request.end(body.get().toString());
        else request.end();

        notifyRequestSent(request.method().name(), request.absoluteURI(), body.orElse(JsonObject.EMPTY_JSON_OBJECT));

        return requestFuture;
    }

    // Visible for inheriting
    void notifyRequestSent(final String httpMethod, final String uri, final JsonObject data) {
        eventBus().sendEvent(MessageSent.of(data), MessageSent.class);
    }

    // Visible for inheriting
    void notifyResponseRecv(final JsonObject data) {
        eventBus().sendEvent(MessageReceived.of(data), MessageReceived.class);
    }

    // Visible for inheriting
    abstract HttpClientRequest buildHttpRequest();

    // Visible for inheriting
    abstract Optional<JsonObject> requestBody(final Request msg);

    static abstract class GetHttpRequesting extends HttpRequesting {
        GetHttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper) {
            super(helper, eventBusHelper);
        }

        @Override
        final HttpClientRequest buildHttpRequest() {
            return httpHelper().getRequest(subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Request body) {
            return Optional.empty();
        }

        abstract String subPath();
    }

    static class ServerInfoHttpRequesting extends GetHttpRequesting {
        ServerInfoHttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper) {
            super(helper, eventBusHelper);
        }

        @Override
        final String subPath() {
            return "info";
        }
    }

    static class LongPollHttpRequesting extends GetHttpRequesting {
        private final long sid;
        private final int maxEvents = 10;

        LongPollHttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper, final long sessionId) {
            super(helper, eventBusHelper);
            this.sid = sessionId;
        }

        @Override
        final String subPath() {
            return sid + "?maxev=" + maxEvents;
        }
    }

    static abstract class PostHttpRequesting extends HttpRequesting {
        PostHttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper) {
            super(helper, eventBusHelper);
        }

        @Override
        final HttpClientRequest buildHttpRequest() {
            return httpHelper().postRequest(subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Request body) {
            final JsonObjectBuilder builder = Json.createObjectBuilder(body.root());
            builder.add(Constants.REQ_FIELD_REQUEST_TYPE, body.request().method());
            builder.add(Constants.REQ_FIELD_TRANSACTION, body.transaction().id());
            return Optional.of(builder.build());
        }

        abstract String subPath();
    }

    static class GlobalBasedHttpRequesting extends PostHttpRequesting {
        GlobalBasedHttpRequesting(HttpTransportHelper helper, EventBusHelper eventBusHelper) {
            super(helper, eventBusHelper);
        }

        @Override
        final String subPath() {
            return "";
        }
    }

    static class CreateSessionHttpRequesting extends GlobalBasedHttpRequesting {
        CreateSessionHttpRequesting(HttpTransportHelper helper, EventBusHelper eventBusHelper) {
            super(helper, eventBusHelper);
        }

        @Override
        void notifyResponseRecv(JsonObject data) {
            super.notifyResponseRecv(data);

            // TODO check success in data
            final OptionalLong sessionId = Constants.sessionId(data);
            sessionId.ifPresent(
                id -> eventBus().sendEvent(SessionCreated.of(id), SessionCreated.class)
            );
        }
    }

    static class SessionBasedPostHttpRequesting extends PostHttpRequesting {
        final long sid;

        SessionBasedPostHttpRequesting(final HttpTransportHelper helper, final EventBusHelper eventBusHelper, final long sessionId) {
            super(helper, eventBusHelper);
            this.sid = sessionId;
        }

        @Override
        final String subPath() {
            return String.valueOf(sid);
        }
    }

    static class DestroySessionHttpRequesting extends SessionBasedPostHttpRequesting {
        DestroySessionHttpRequesting(HttpTransportHelper helper, EventBusHelper eventBusHelper, final long sessionId) {
            super(helper, eventBusHelper, sessionId);
        }

        @Override
        void notifyResponseRecv(JsonObject data) {
            super.notifyResponseRecv(data);

            // TODO check success in data
            eventBus().sendEvent(SessionDestroyed.of(sid), SessionDestroyed.class);
        }
    }

    static class PluginPostHttpRequesting extends PostHttpRequesting {
        private final long sid;
        private final long hid;

        PluginPostHttpRequesting(
            final HttpTransportHelper helper,
            final EventBusHelper eventBusHelper,
            final long sessionId,
            final long pluginId
        ) {
            super(helper, eventBusHelper);
            this.sid = sessionId;
            this.hid = pluginId;
        }

        @Override
        final String subPath() {
            return sid + "/" + hid;
        }
    }
}
