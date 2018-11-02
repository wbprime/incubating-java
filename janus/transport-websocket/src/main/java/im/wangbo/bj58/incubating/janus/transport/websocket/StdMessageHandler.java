package im.wangbo.bj58.incubating.janus.transport.websocket;

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
import javax.json.JsonObject;
import javax.json.JsonValue;

import im.wangbo.bj58.incubating.janus.PluginHandle;
import im.wangbo.bj58.incubating.janus.ServerInfo;
import im.wangbo.bj58.incubating.janus.Session;
import im.wangbo.bj58.incubating.janus.Transaction;
import im.wangbo.bj58.incubating.janus.transport.JanusAckResponse;
import im.wangbo.bj58.incubating.janus.transport.JanusErrorResponse;
import im.wangbo.bj58.incubating.janus.transport.JanusEventResponse;
import im.wangbo.bj58.incubating.janus.transport.JanusSuccessResponse;
import im.wangbo.bj58.incubating.janus.transport.ResponseHandler;
import im.wangbo.bj58.incubating.janus.transport.ServerInfoResponse;
import im.wangbo.bj58.incubating.janus.transport.UnknownResponse;
import im.wangbo.bj58.incubating.janus.transport.WebrtcHangupResponse;
import im.wangbo.bj58.incubating.janus.transport.WebrtcMediaResponse;
import im.wangbo.bj58.incubating.janus.transport.WebrtcSlowlinkResponse;
import im.wangbo.bj58.incubating.janus.transport.WebrtcUpResponse;

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

    private void handleUnknownMessage(final String msg) {
        parse(msg).ifPresent(
                v -> messageHandler.handle(UnknownResponse.create(v))
        );
    }

    @Override
    public void accept(final String msg) {
        LOGGER.debug("Received message: {}", msg);

        final Optional<JsonObject> json = parse(msg);
        final Optional<TypeResp> resp = json.map(TypeResp::fromJson);
        if (resp.isPresent()) {
            final String respType = resp.get().getResponseType();
            switch (respType) {
                default: {
                    handleUnknownMessage(msg);
                }
                break;
                case TypeResp.TYPE_ACK: {
                    final Optional<AckResp> response = json.map(AckResp::fromJson);

                    if (response.isPresent()) {
                        final AckResp r = response.get();
                        messageHandler.handle(
                                JanusAckResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_ERROR: {
                    final Optional<ErrorResp> response = json.map(ErrorResp::fromJson);

                    if (response.isPresent()) {
                        final ErrorResp r = response.get();
                        messageHandler.handle(
                                JanusErrorResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .errorCode(r.getError().getCode())
                                        .errorMessage(r.getError().getMessage())
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_EVENT: {
                    final Optional<EventResp> response = json.map(EventResp::fromJson);

                    if (response.isPresent()) {
                        final EventResp r = response.get();
                        messageHandler.handle(
                                JanusEventResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .pluginHandle(PluginHandle.of(Session.of(0L), r.getPluginId()))
                                        .data(JsonValue.EMPTY_JSON_OBJECT) // TODO
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_HANGUP: {
                    final Optional<HangupResp> response = json.map(HangupResp::fromJson);

                    if (response.isPresent()) {
                        final HangupResp r = response.get();
                        messageHandler.handle(
                                WebrtcHangupResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .pluginHandle(PluginHandle.of(Session.of(r.getSessionId()), r.getPluginId()))
                                        .message(r.getMessage())
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_MEDIA: {
                    final Optional<MediaResp> response = json.map(MediaResp::fromJson);

                    if (response.isPresent()) {
                        final MediaResp r = response.get();
                        messageHandler.handle(
                                WebrtcMediaResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .pluginHandle(PluginHandle.of(Session.of(r.getSessionId()), r.getPluginId()))
                                        .mediaType(r.getMediaType())
                                        .isReceiving(r.getReceiving())
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_SERVER_INFO: {
                    final Optional<ServerInfoResp> response = json.map(ServerInfoResp::fromJson);

                    if (response.isPresent()) {
                        final ServerInfoResp r = response.get();

                        final Map<String, ServerInfo.PluginDesc> plugins;
                        {
                            final Map<String, ServerInfoResp.PluginDesc> ps = r.getPlugins();
                            if (null != ps) {
                                plugins = Maps.newHashMapWithExpectedSize(ps.size());
                                for (Map.Entry<String, ServerInfoResp.PluginDesc> entry : ps.entrySet()) {
                                    final ServerInfoResp.PluginDesc desc = entry.getValue();
                                    plugins.put(
                                            entry.getKey(),
                                            ServerInfo.PluginDesc.builder()
                                                    .name(desc.getName())
                                                    .author(desc.getAuthor())
                                                    .description(desc.getDescription())
                                                    .versionNumber(desc.getVersion())
                                                    .versionString(desc.getVersionDescription())
                                                    .build()
                                    );
                                }
                            } else {
                                plugins = Collections.emptyMap();
                            }
                        }
                        final Map<String, ServerInfo.TransportDesc> transports;
                        {
                            final Map<String, ServerInfoResp.TransportDesc> ts = r.getTransports();
                            if (null != ts) {
                                transports = Maps.newHashMapWithExpectedSize(ts.size());
                                for (Map.Entry<String, ServerInfoResp.TransportDesc> entry : ts.entrySet()) {
                                    final ServerInfoResp.TransportDesc desc = entry.getValue();
                                    transports.put(
                                            entry.getKey(),
                                            ServerInfo.TransportDesc.builder()
                                                    .name(desc.getName())
                                                    .author(desc.getAuthor())
                                                    .description(desc.getDescription())
                                                    .versionNumber(desc.getVersion())
                                                    .versionString(desc.getVersionDescription())
                                                    .build()
                                    );
                                }
                            } else {
                                transports = Collections.emptyMap();
                            }
                        }
                        final Map<String, ServerInfo.EvHandlerDesc> evHandlers;
                        {
                            final Map<String, ServerInfoResp.EvHandlerDesc> es = r.getEventHandlers();
                            if (null != es) {
                                evHandlers = Maps.newHashMapWithExpectedSize(es.size());
                                for (Map.Entry<String, ServerInfoResp.EvHandlerDesc> entry : es.entrySet()) {
                                    final ServerInfoResp.EvHandlerDesc desc = entry.getValue();
                                    evHandlers.put(
                                            entry.getKey(),
                                            ServerInfo.EvHandlerDesc.builder()
                                                    .name(desc.getName())
                                                    .author(desc.getAuthor())
                                                    .description(desc.getDescription())
                                                    .versionNumber(desc.getVersion())
                                                    .versionString(desc.getVersionDescription())
                                                    .build()
                                    );
                                }
                            } else {
                                evHandlers = Collections.emptyMap();
                            }
                        }

                        messageHandler.handle(
                                ServerInfoResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .server(
                                                ServerInfo.ServerDesc.builder()
                                                        .name(r.getName())
                                                        .author(r.getAuthor())
                                                        .sessionTimeout(Duration.ofSeconds(r.getSessionTimeout()))
                                                        .versionNumber(r.getVersion())
                                                        .versionString(r.getVersionDescription())
                                                        .build()
                                        )
                                        .plugins(plugins)
                                        .transports(transports)
                                        .eventHandlers(evHandlers)
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_SLOWLINK: {
                    final Optional<SlowlinkResp> response = json.map(SlowlinkResp::fromJson);

                    if (response.isPresent()) {
                        final SlowlinkResp r = response.get();
                        messageHandler.handle(
                                WebrtcSlowlinkResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .pluginHandle(PluginHandle.of(Session.of(r.getSessionId()), r.getPluginId()))
                                        .numberOfNacks(r.getNumberOfNacks())
                                        .isUplink(r.getUplink())
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_SUCCESS: {
                    final Optional<SuccessResp> response = json.map(SuccessResp::fromJson);

                    if (response.isPresent()) {
                        final SuccessResp r = response.get();
                        messageHandler.handle(
                                JanusSuccessResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .data(r.getData())
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
                case TypeResp.TYPE_WEBRTCUP: {
                    final Optional<WebrtcUpResp> response = json.map(WebrtcUpResp::fromJson);

                    if (response.isPresent()) {
                        final WebrtcUpResp r = response.get();
                        messageHandler.handle(
                                WebrtcUpResponse.builder()
                                        .transaction(Transaction.of(r.getTransactionId()))
                                        .pluginHandle(PluginHandle.of(Session.of(r.getSessionId()), r.getPluginId()))
                                        .build()
                        );
                    } else {
                        handleUnknownMessage(msg);
                    }
                }
                break;
            }
        } else {
            // Handle invalid json message
        }
    }

    private Optional<JsonObject> parse(final String msg) {
        try {
            final JsonObject response = Json.createReader(new StringReader(msg)).readObject();
            return Optional.of(response);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
