package im.wangbo.bj58.janus.schema.transport;

import com.google.common.base.Splitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import im.wangbo.bj58.janus.schema.RequestMethod;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientRequest;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
abstract class HttpRequesting {
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequesting.class);

    private final HttpTransportHelper http;

    HttpRequesting(final HttpTransportHelper helper) {
        this.http = helper;
    }

    static HttpRequesting create(final Transport.RequestMessage msg, final HttpTransportHelper helper) {
        final OptionalLong sessionId = msg.sessionId().map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());
        final OptionalLong pluginId = msg.pluginId().map(id -> OptionalLong.of(id.id())).orElse(OptionalLong.empty());

        switch (msg.request().method()) {
            case RequestMethod.SERVER_INFO:
                return new ServerInfoHttpRequesting(helper);
            case RequestMethod.CREATE_SESSION:
                return new CreateSessionHttpRequesting(helper);
            case RequestMethod.DESTROY_SESSION: // fall through
            case RequestMethod.ATTACH_PLUGIN: {
                if (sessionId.isPresent()) {
                    return new SessionBasedPostHttpRequesting(helper, sessionId.getAsLong());
                } else {
                    throw new IllegalArgumentException("Missing [sessionId] in " + msg);
                }
            }
            case RequestMethod.DETACH_PLUGIN: // fall through
            case RequestMethod.HANGUP_PLUGIN: // fall through
            case RequestMethod.SEND_MESSAGE: // fall through
            case RequestMethod.TRICKLE: {
                if (sessionId.isPresent() && pluginId.isPresent()) {
                    return new PluginPostHttpRequesting(helper, sessionId.getAsLong(), pluginId.getAsLong());
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
                        return new PluginPostHttpRequesting(helper, sessionId.getAsLong(), pluginId.getAsLong());
                    } else {
                        return new SessionBasedPostHttpRequesting(helper, sessionId.getAsLong());
                    }
                } else {
                    return new GlobalBasedHttpRequesting(helper);
                }
        }
    }

    final HttpTransportHelper httpHelper() {
        return http;
    }

    private void handleResponseAsJson(
            final Buffer buf, final BiConsumer<JsonObject, Throwable> handler
    ) {
        final String res = buf.toString(StandardCharsets.UTF_8);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Received remote HTTP response: ");
            Splitter.on('\n').splitToList(res).forEach(line -> LOG.debug(" => {}", line));
        }

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
                                handler.accept((JsonObject) json, null);
                            } else {
                                handler.accept(null, new IllegalArgumentException(
                                        "Expected only JSON object in top level array but not: " + res));
                            }
                        }
                );
                break;
            case OBJECT:
                handler.accept((JsonObject) json, null);
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
            final Transport.RequestMessage msg, final BiConsumer<JsonObject, Throwable> responseHandler
    ) {
        final Optional<JsonObject> body = requestBody(msg);

        // Response
        final CompletableFuture<Void> requestFuture = new CompletableFuture<>();

        final HttpClientRequest request = buildHttpRequest();

        final HttpClientRequest request2 = request.endHandler(ignored -> {
            LOG.debug("Request ended to \"{}\"", request.absoluteURI());
            requestFuture.complete(null);
        }).exceptionHandler(requestFuture::completeExceptionally)
                .handler(res -> res.exceptionHandler(ex -> responseHandler.accept(null, ex))
                        .bodyHandler(buf -> handleResponseAsJson(buf, responseHandler))
                );

        if (body.isPresent()) request2.end(body.get().toString());
        else request2.end();
        LOG.debug("Request sent to \"{}\"", request.absoluteURI());

        return requestFuture;
    }

    abstract HttpClientRequest buildHttpRequest();

    abstract Optional<JsonObject> requestBody(final Transport.RequestMessage msg);

    static abstract class GetHttpRequesting extends HttpRequesting {
        GetHttpRequesting(final HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        final HttpClientRequest buildHttpRequest() {
            return httpHelper().getRequest(subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Transport.RequestMessage body) {
            return Optional.empty();
        }

        abstract String subPath();
    }

    static class ServerInfoHttpRequesting extends GetHttpRequesting {
        ServerInfoHttpRequesting(final HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        final String subPath() {
            return "info";
        }
    }

    static class LongPollHttpRequesting extends GetHttpRequesting {
        private final long sid;
        private final int maxEvents = 10;

        LongPollHttpRequesting(final HttpTransportHelper helper, final long sessionId) {
            super(helper);
            this.sid = sessionId;
        }

        @Override
        final String subPath() {
            return sid + "?maxev=" + maxEvents;
        }
    }

    static abstract class PostHttpRequesting extends HttpRequesting {
        PostHttpRequesting(final HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        final HttpClientRequest buildHttpRequest() {
            return httpHelper().postRequest(subPath());
        }

        @Override
        final Optional<JsonObject> requestBody(final Transport.RequestMessage body) {
            final JsonObjectBuilder builder = Json.createObjectBuilder(body.root());
            builder.add(Constants.REQ_FIELD_REQUEST_TYPE, body.request().method());
            builder.add(Constants.REQ_FIELD_TRANSACTION, body.transaction().id());
            return Optional.of(builder.build());
        }

        abstract String subPath();
    }

    static class GlobalBasedHttpRequesting extends PostHttpRequesting {
        GlobalBasedHttpRequesting(HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        final String subPath() {
            return "";
        }
    }

    static class CreateSessionHttpRequesting extends GlobalBasedHttpRequesting {
        CreateSessionHttpRequesting(HttpTransportHelper helper) {
            super(helper);
        }
    }

    static class SessionBasedPostHttpRequesting extends PostHttpRequesting {
        private final long sid;

        SessionBasedPostHttpRequesting(final HttpTransportHelper helper, final long sessionId) {
            super(helper);
            this.sid = sessionId;
        }

        @Override
        final String subPath() {
            return String.valueOf(sid);
        }
    }

    static class PluginPostHttpRequesting extends PostHttpRequesting {
        private final long sid;
        private final long hid;

        PluginPostHttpRequesting(
                final HttpTransportHelper helper,
                final long sessionId,
                final long pluginId
        ) {
            super(helper);
            this.sid = sessionId;
            this.hid = pluginId;
        }

        @Override
        final String subPath() {
            return sid + "/" + hid;
        }
    }
}
