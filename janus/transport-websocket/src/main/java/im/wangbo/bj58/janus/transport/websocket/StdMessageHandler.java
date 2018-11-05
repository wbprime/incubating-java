package im.wangbo.bj58.janus.transport.websocket;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;

import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.ServerInfo;
import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.Transaction;
import im.wangbo.bj58.janus.transport.InvalidResponse;
import im.wangbo.bj58.janus.transport.JanusAckResponse;
import im.wangbo.bj58.janus.transport.JanusErrorResponse;
import im.wangbo.bj58.janus.transport.JanusEventResponse;
import im.wangbo.bj58.janus.transport.JanusSuccessResponse;
import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.ServerInfoResponse;
import im.wangbo.bj58.janus.transport.UnknownResponse;
import im.wangbo.bj58.janus.transport.WebrtcHangupResponse;
import im.wangbo.bj58.janus.transport.WebrtcMediaResponse;
import im.wangbo.bj58.janus.transport.WebrtcSlowlinkResponse;
import im.wangbo.bj58.janus.transport.WebrtcUpResponse;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class StdMessageHandler implements Consumer<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StdMessageHandler.class);

    private final ResponseHandler messageHandler;

    StdMessageHandler(final ResponseHandler responseHandler) {
        this.messageHandler = responseHandler;
    }

    private Transaction transactionFromJson(final JsonObject json) {
        return Transaction.of(json.getString("transaction"));
    }

    private Session sessionFromJson(final JsonObject json) {
        return Session.of(json.getJsonNumber("session_id").longValue());
    }

    private long pluginHandleFromJson(final JsonObject json) {
        return json.getJsonNumber("sender").longValue();
    }

    private JanusAckResponse ackFromJson(final JsonObject json) {
        return JanusAckResponse.builder()
                .transaction(transactionFromJson(json))
                .build();
    }

    private JanusErrorResponse errorFromJson(final JsonObject json) {
        final JsonObject inner = json.getJsonObject("error");
        return JanusErrorResponse.builder()
                .transaction(transactionFromJson(json))
                .errorCode(inner.getInt("code"))
                .errorMessage(inner.getString("reason"))
                .build();
    }

    private JanusEventResponse eventFromJson(final JsonObject json) {
//        final JsonObject inner = json.getJsonObject("pluginData");
        return JanusEventResponse.builder()
                .transaction(transactionFromJson(json))
                .pluginHandle(PluginHandle.of(Session.of(0L), pluginHandleFromJson(json)))
                .data(JsonValue.EMPTY_JSON_OBJECT) // TODO
                .build();
    }

    private WebrtcHangupResponse hangupFromJson(final JsonObject json) {
        return WebrtcHangupResponse.builder()
                .transaction(transactionFromJson(json))
                .pluginHandle(PluginHandle.of(sessionFromJson(json), pluginHandleFromJson(json)))
                .message(json.getString("reason"))
                .build();
    }

    private WebrtcMediaResponse mediaFromJson(final JsonObject json) {
        return WebrtcMediaResponse.builder()
                .transaction(transactionFromJson(json))
                .pluginHandle(PluginHandle.of(sessionFromJson(json), pluginHandleFromJson(json)))
                .mediaType(json.getString("type"))
                .isReceiving(json.getBoolean("receiving"))
                .build();
    }

    private WebrtcSlowlinkResponse slowlinkFromJson(final JsonObject json) {
        return WebrtcSlowlinkResponse.builder()
                .transaction(transactionFromJson(json))
                .pluginHandle(PluginHandle.of(sessionFromJson(json), pluginHandleFromJson(json)))
                .numberOfNacks(json.getInt("nacks"))
                .isUplink(json.getBoolean("uplink"))
                .build();
    }

    private ServerInfoResponse serverInfoFromJson(final JsonObject json) {
        final Map<String, ServerInfo.PluginDesc> plugins;
        {
            final JsonObject inner = json.getJsonObject("plugins");
            if (inner.isEmpty()) {
                plugins = Collections.emptyMap();
            } else {
                plugins = Maps.newHashMapWithExpectedSize(inner.size());
                inner.forEach(
                        (k, v) -> {
                            final JsonObject o = (JsonObject) v;
                            plugins.put(
                                    k,
                                    ServerInfo.PluginDesc.builder()
                                            .name(o.getString("name"))
                                            .author(o.getString("author"))
                                            .description(o.getString("description"))
                                            .versionNumber(o.getInt("version"))
                                            .versionString(o.getString("version_string"))
                                            .build()
                            );
                        }
                );
            }
        }
        final Map<String, ServerInfo.TransportDesc> transports;
        {
            final JsonObject inner = json.getJsonObject("transports");
            if (inner.isEmpty()) {
                transports = Collections.emptyMap();
            } else {
                transports = Maps.newHashMapWithExpectedSize(inner.size());
                inner.forEach(
                        (k, v) -> {
                            final JsonObject o = (JsonObject) v;
                            transports.put(
                                    k,
                                    ServerInfo.TransportDesc.builder()
                                            .name(o.getString("name"))
                                            .author(o.getString("author"))
                                            .description(o.getString("description"))
                                            .versionNumber(o.getInt("version"))
                                            .versionString(o.getString("version_string"))
                                            .build()
                            );
                        }
                );
            }
        }
        final Map<String, ServerInfo.EvHandlerDesc> evHandlers;
        {
            final JsonObject inner = json.getJsonObject("transports");
            if (inner.isEmpty()) {
                evHandlers = Collections.emptyMap();
            } else {
                evHandlers = Maps.newHashMapWithExpectedSize(inner.size());
                inner.forEach(
                        (k, v) -> {
                            final JsonObject o = (JsonObject) v;
                            evHandlers.put(
                                    k,
                                    ServerInfo.EvHandlerDesc.builder()
                                            .name(o.getString("name"))
                                            .author(o.getString("author"))
                                            .description(o.getString("description"))
                                            .versionNumber(o.getInt("version"))
                                            .versionString(o.getString("version_string"))
                                            .build()
                            );
                        }
                );
            }
        }

        return ServerInfoResponse.builder()
                .transaction(transactionFromJson(json))
                .server(ServerInfo.ServerDesc.builder()
                        .name(json.getString("name"))
                        .author(json.getString("author"))
                        .sessionTimeout(Duration.ofSeconds(json.getInt("session-timeout")))
                        .versionNumber(json.getInt("version"))
                        .versionString(json.getString("version_string"))
                        .build()
                )
                .plugins(plugins)
                .transports(transports)
                .eventHandlers(evHandlers)
                .build();
    }

    private JanusSuccessResponse successFromJson(final JsonObject json) {
        final JanusSuccessResponse.Builder builder = JanusSuccessResponse.builder()
                .transaction(transactionFromJson(json));
        if (json.containsKey("data")) {
            return builder.data(json.getJsonObject("data")).build();
        } else {
            return builder.data(JsonObject.EMPTY_JSON_OBJECT).build();
        }
    }

    private WebrtcUpResponse webrtcupFromJson(final JsonObject json) {
        return WebrtcUpResponse.builder()
                .transaction(transactionFromJson(json))
                .pluginHandle(PluginHandle.of(sessionFromJson(json), pluginHandleFromJson(json)))
                .build();
    }

    @Override
    public void accept(final String msg) {
        LOGGER.debug("Received message: {}", msg);

        final Optional<JsonObject> jsonOpt = parse(msg);
        if (jsonOpt.isPresent()) {
            final JsonObject json = jsonOpt.get();
            final String respType = json.getString("janus");
            switch (respType) {
                default:
                    messageHandler.handle(UnknownResponse.create(json));
                    break;
                case TypeResp.TYPE_ACK:
                    messageHandler.handle(ackFromJson(json));
                    break;
                case TypeResp.TYPE_ERROR:
                    messageHandler.handle(errorFromJson(json));
                    break;
                case TypeResp.TYPE_EVENT:
                    messageHandler.handle(eventFromJson(json));
                    break;
                case TypeResp.TYPE_HANGUP:
                    messageHandler.handle(hangupFromJson(json));
                    break;
                case TypeResp.TYPE_MEDIA:
                    messageHandler.handle(mediaFromJson(json));
                    break;
                case TypeResp.TYPE_SERVER_INFO:
                    messageHandler.handle(serverInfoFromJson(json));
                    break;
                case TypeResp.TYPE_SLOWLINK:
                    messageHandler.handle(slowlinkFromJson(json));
                    break;
                case TypeResp.TYPE_SUCCESS:
                    messageHandler.handle(successFromJson(json));
                    break;
                case TypeResp.TYPE_WEBRTCUP:
                    messageHandler.handle(webrtcupFromJson(json));
                    break;
            }
        } else {
            // Invalid response, failed to parse response as JSON
            messageHandler.handle(InvalidResponse.create(msg));
        }
    }

    private Optional<JsonObject> parse(final String msg) {
        try (final StringReader reader = new StringReader(msg)) {
            final JsonObject response = Json.createReader(reader).readObject();
            return Optional.of(response);
        } catch (JsonException ex) {
            return Optional.empty();
        }
    }
}
