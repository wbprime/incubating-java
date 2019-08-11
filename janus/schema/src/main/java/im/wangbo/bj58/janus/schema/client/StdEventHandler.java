package im.wangbo.bj58.janus.schema.client;

import com.google.common.collect.Maps;
import im.wangbo.bj58.janus.schema.event.JsonableEvent;
import im.wangbo.bj58.janus.schema.transport.PluginId;
import im.wangbo.bj58.janus.schema.transport.SessionId;
import im.wangbo.bj58.janus.schema.transport.TransactionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * TODO add brief description here
 * <p>
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class StdEventHandler implements Consumer<JsonObject> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StdEventHandler.class);

    private final Iterable<EventHandler> handlers;

    private final Consumer<Throwable> exHandler = ex -> {
        LOGGER.warn("Internal error", ex);
    };

    StdEventHandler(final Iterable<EventHandler> responseHandler) {
        this.handlers = responseHandler;
    }

    static StdEventHandler of(final Iterable<EventHandler> handlers) {
        return new StdEventHandler(handlers);
    }

    private TransactionId transactionFromJson(final JsonObject json) {
        return TransactionId.of(json.getString("transaction"));
    }

    private SessionId sessionFromJson(final JsonObject json) {
        return SessionId.of(json.getJsonNumber("session_id").longValue());
    }

    private PluginId pluginIdFromJson(final JsonObject json) {
        return PluginId.of(json.getJsonNumber("sender").longValue());
    }

    private JanusAckEvent ackFromJson(final JsonObject json) {
        return JanusAckEvent.of(transactionFromJson(json));
    }

    private JanusErrorEvent errorFromJson(final JsonObject json) {
        final JsonObject inner = json.getJsonObject("error");
        return JanusErrorEvent.of(
            transactionFromJson(json),
            Error.of(inner.getInt("code"), inner.getString("reason"))
        );
    }

    private JanusNotifyEvent notifyFromJson(final JsonObject json) {
        final JsonObject inner = json.getJsonObject("pluginData");
        return JanusNotifyEvent.of(
            transactionFromJson(json),
            pluginIdFromJson(json), inner);
    }

    private WebrtcDownEvent hangupFromJson(final JsonObject json) {
        return WebrtcDownEvent.builder()
            .transaction(transactionFromJson(json))
            .sessionId(sessionFromJson(json))
            .pluginId(pluginIdFromJson(json))
            .message(json.getString("reason"))
            .build();
    }

    private WebrtcMediaEvent mediaFromJson(final JsonObject json) {
        return WebrtcMediaEvent.builder()
            .transaction(transactionFromJson(json))
            .sessionId(sessionFromJson(json))
            .pluginId(pluginIdFromJson(json))
            .mediaType(json.getString("type"))
            .isReceiving(json.getBoolean("receiving"))
            .build();
    }

    private WebrtcSlowlinkEvent slowlinkFromJson(final JsonObject json) {
        return WebrtcSlowlinkEvent.builder()
            .transaction(transactionFromJson(json))
            .sessionId(sessionFromJson(json))
            .pluginId(pluginIdFromJson(json))
            .numberOfNacks(json.getInt("nacks"))
            .isUplink(json.getBoolean("uplink"))
            .build();
    }

    private ServerInfoEvent serverInfoFromJson(final JsonObject json) {
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

        return ServerInfoEvent.builder()
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

    private JanusSuccessEvent successFromJson(final JsonObject json) {
        final JanusSuccessEvent.Builder builder = JanusSuccessEvent.builder()
            .transaction(transactionFromJson(json));
        if (json.containsKey("data")) {
            return builder.data(json.getJsonObject("data")).build();
        } else {
            return builder.data(JsonObject.EMPTY_JSON_OBJECT).build();
        }
    }

    private WebrtcUpEvent webrtcupFromJson(final JsonObject json) {
        return WebrtcUpEvent.builder()
            .transaction(transactionFromJson(json))
            .sessionId(sessionFromJson(json))
            .pluginId(pluginIdFromJson(json))
            .build();
    }

    @Override
    public void accept(final JsonObject json) {
        LOGGER.debug("Received message: {}", json);

        final JsonableEvent event;
        final String respType = json.getString("janus");
        switch (respType) {
            default:
                event = UnknownEvent.of(json);
                break;
            case "ack":
                event = ackFromJson(json);
                break;
            case "error":
                event = errorFromJson(json);
                break;
            case "event":
                event = notifyFromJson(json);
                break;
            case "hangup":
                event = hangupFromJson(json);
                break;
            case "media":
                event = mediaFromJson(json);
                break;
            case "server_info":
                event = serverInfoFromJson(json);
                break;
            case "slow_link":
                event = slowlinkFromJson(json);
                break;
            case "success":
                event = successFromJson(json);
                break;
            case "webrtc_up":
                event = webrtcupFromJson(json);
                break;
        }

        handlers.forEach(
            h -> {
                try {
                    h.accept(event);
                } catch (Exception ex) {
                    exHandler.accept(ex);
                }
            }
        );
    }
}
