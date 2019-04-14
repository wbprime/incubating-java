package im.wangbo.bj58.janus.schema.transport;

import com.google.common.base.Splitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
        final OptionalLong sessionId = msg.sessionId();
        final OptionalLong pluginId = msg.pluginId();
        switch (msg.request()) {
            case SERVER_INFO:
                return new ServerInfoHttpRequesting(helper);
            case CREATE_SESSION:
                return new PostHttpRequesting(helper);
            case DESTROY_SESSION: // fall through
            case ATTACH_PLUGIN: {
                if (sessionId.isPresent()) {
                    return new SessionPostHttpRequesting(helper, sessionId.getAsLong());
                } else {
                    throw new IllegalArgumentException("Missing [sessionId] in " + msg);
                }
            }
            case DETACH_PLUGIN: // fall through
            case HANGUP_PLUGIN: // fall through
            case PLUGIN_MESSAGE: // fall through
            case TRICKLE: {
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
                throw new IllegalArgumentException("Unsupported " + msg);
        }
    }

    final HttpTransportHelper httpHelper() {
        return http;
    }

    private CompletionStage<JsonObject> convert(final Buffer buf) {
        final String res = buf.toString(StandardCharsets.UTF_8);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Receive remote HTTP response: ");
            Splitter.on('\n').splitToList(res).forEach(line -> LOG.debug(" => {}", line));
        }

        try {
            final JsonObject json = Json.createReader(new StringReader(res)).readObject();
            return Futures.completed(json);
        } catch (JsonException ex) {
            return Futures.failed(ex);
        }
    }

    final CompletionStage<JsonObject> request(final Transport.RequestMessage msg, final CompletableFuture<Void> sent) {
        final Optional<JsonObject> body = body(msg);

        final CompletableFuture<JsonObject> future = new CompletableFuture<>();

        final HttpClientRequest request = request();

        final HttpClientRequest request2 = request.endHandler(ignored -> {
            LOG.debug("Request ended to \"{}\"", request.absoluteURI());
            sent.complete(null);
        }).exceptionHandler(sent::completeExceptionally)
                .handler(
                        res -> res.exceptionHandler(future::completeExceptionally)
                                .bodyHandler(
                                        buf -> convert(buf).whenComplete(
                                                (json, ex) -> {
                                                    if (null == ex) future.complete(json);
                                                    else future.completeExceptionally(ex);
                                                }
                                        )
                                )
                );

        if (body.isPresent()) request2.end(body.get().toString());
        else request2.end();
        LOG.debug("Request sent to \"{}\"", request.absoluteURI());

        return future;
    }

    abstract HttpClientRequest request();

    abstract Optional<JsonObject> body(final Transport.RequestMessage msg);

    static class ServerInfoHttpRequesting extends HttpRequesting {
        ServerInfoHttpRequesting(final HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        final HttpClientRequest request() {
            return httpHelper().getRequest("info");
        }

        @Override
        final Optional<JsonObject> body(Transport.RequestMessage msg) {
            return Optional.empty();
        }
    }

    static class PostHttpRequesting extends HttpRequesting {
        PostHttpRequesting(final HttpTransportHelper helper) {
            super(helper);
        }

        @Override
        HttpClientRequest request() {
            return httpHelper().postRequest("");
        }

        @Override
        final Optional<JsonObject> body(final Transport.RequestMessage body) {
            final JsonObjectBuilder builder = Json.createObjectBuilder(body.root());
            builder.add(Constants.REQ_FIELD_REQUEST_TYPE, body.request().value());
            builder.add(Constants.REQ_FIELD_TRANSACTION, body.transaction());
            return Optional.of(builder.build());
        }
    }

    static class SessionPostHttpRequesting extends PostHttpRequesting {
        private final long sid;

        SessionPostHttpRequesting(final HttpTransportHelper helper, final long sessionId) {
            super(helper);
            this.sid = sessionId;
        }

        @Override
        HttpClientRequest request() {
            return httpHelper().postRequest("" + sid);
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
        HttpClientRequest request() {
            return httpHelper().postRequest(sid + "/" + hid);
        }
    }
}
