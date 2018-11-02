package im.wangbo.bj58.janus.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import im.wangbo.bj58.janus.transport.UnknownResponse;
import im.wangbo.bj58.janus.transport.JanusAckResponse;
import im.wangbo.bj58.janus.transport.JanusErrorResponse;
import im.wangbo.bj58.janus.transport.JanusEventResponse;
import im.wangbo.bj58.janus.transport.JanusSuccessResponse;
import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.ServerInfoResponse;
import im.wangbo.bj58.janus.transport.WebrtcHangupResponse;
import im.wangbo.bj58.janus.transport.WebrtcMediaResponse;
import im.wangbo.bj58.janus.transport.WebrtcSlowlinkResponse;
import im.wangbo.bj58.janus.transport.WebrtcUpResponse;

import static com.google.common.base.MoreObjects.firstNonNull;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class StdResponseHandler implements ResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(StdResponseHandler.class);

    private final JanusSuccessHandler janusSuccessHandler;
    private final JanusErrorHandler janusErrorHandler;
    private final JanusAckHandler janusAckHandler;

    private final JanusEventHandler janusEventHandler;

    private final WebrtcUpHandler webrtcUpHandler;
    private final WebrtcHangupHandler webrtcHangupHandler;
    private final WebrtcMediaHandler webrtcMediaHandler;
    private final WebrtcSlowlinkHandler webrtcSlowlinkHandler;

    private final ServerInfoHandler serverInfoHandler;

    private StdResponseHandler(
            final JanusSuccessHandler janusSuccessHandler,
            final JanusErrorHandler janusErrorHandler,
            final JanusAckHandler janusAckHandler1,
            final JanusEventHandler janusEventHandler,
            final WebrtcUpHandler webrtcUpHandler,
            final WebrtcHangupHandler webrtcHangupHandler,
            final WebrtcMediaHandler webrtcMediaHandler,
            final WebrtcSlowlinkHandler webrtcSlowlinkHandler,
            final ServerInfoHandler serverInfoHandler
    ) {
        this.janusSuccessHandler = janusSuccessHandler;
        this.janusErrorHandler = janusErrorHandler;
        this.janusAckHandler = janusAckHandler1;
        this.janusEventHandler = janusEventHandler;
        this.webrtcUpHandler = webrtcUpHandler;
        this.webrtcHangupHandler = webrtcHangupHandler;
        this.webrtcMediaHandler = webrtcMediaHandler;
        this.webrtcSlowlinkHandler = webrtcSlowlinkHandler;
        this.serverInfoHandler = serverInfoHandler;
    }

    @Override
    public void handle(JanusSuccessResponse response) {
        LOGGER.info("success async response: {}", response);
        janusSuccessHandler.onSuccess(response);
    }

    @Override
    public void handle(JanusAckResponse response) {
        LOGGER.debug("ack async response: {}", response);
        janusAckHandler.onAck(response);
    }

    @Override
    public void handle(JanusErrorResponse response) {
        LOGGER.debug("error async response: {}", response);
        janusErrorHandler.onError(response);
    }

    @Override
    public void handle(JanusEventResponse response) {
        LOGGER.info("event async response: {}", response);
        janusEventHandler.onEvent();
    }

    @Override
    public void handle(WebrtcHangupResponse response) {
        LOGGER.info("hangup async response: {}", response);
        webrtcHangupHandler.onHangup(null, null, null);
    }

    @Override
    public void handle(WebrtcMediaResponse response) {
        LOGGER.info("media async response: {}", response);
        webrtcMediaHandler.onMedia(null, null, null, true);
    }

    @Override
    public void handle(WebrtcUpResponse response) {
        LOGGER.info("webrtcup async response: {}", response);
        webrtcUpHandler.onWebrtcUp(null, null);
    }

    @Override
    public void handle(WebrtcSlowlinkResponse response) {
        LOGGER.info("slowlink async response: {}", response);
        webrtcSlowlinkHandler.onSlowLink(null, null, true, 1);
    }

    @Override
    public void handle(ServerInfoResponse response) {
        LOGGER.info("serverinfo async response: {}", response);
        serverInfoHandler.onServerInfo(response);
    }

    @Override
    public void handle(UnknownResponse response) {
        LOGGER.info("Invalid async response: {}", response);
    }

    static Builder builder() {
        return new Builder();
    }

    static final class Builder {
        @Nullable
        private JanusSuccessHandler janusSuccessHandler;
        @Nullable
        private JanusErrorHandler janusErrorHandler;
        @Nullable
        private JanusAckHandler janusAckHandler;

        @Nullable
        private JanusEventHandler janusEventHandler;

        @Nullable
        private WebrtcUpHandler webrtcUpHandler;
        @Nullable
        private WebrtcHangupHandler webrtcHangupHandler;
        @Nullable
        private WebrtcMediaHandler webrtcMediaHandler;
        @Nullable
        private WebrtcSlowlinkHandler webrtcSlowlinkHandler;

        @Nullable
        private ServerInfoHandler serverInfoHandler;

        Builder janusSuccessHandler(final JanusSuccessHandler handler) {
            this.janusSuccessHandler = handler;
            return this;
        }

        Builder janusErrorHandler(final JanusErrorHandler handler) {
            this.janusErrorHandler = handler;
            return this;
        }

        Builder janusAckHandler(final JanusAckHandler handler) {
            this.janusAckHandler = handler;
            return this;
        }

        Builder janusEventHandler(final JanusEventHandler handler) {
            this.janusEventHandler = handler;
            return this;
        }

        Builder webrtcUpHandler(final WebrtcUpHandler handler) {
            this.webrtcUpHandler = handler;
            return this;
        }

        Builder webrtcHangupHandler(final WebrtcHangupHandler handler) {
            this.webrtcHangupHandler = handler;
            return this;
        }

        Builder webrtcMediaHandler(final WebrtcMediaHandler handler) {
            this.webrtcMediaHandler = handler;
            return this;
        }

        Builder webrtcSlowlinkHandler(final WebrtcSlowlinkHandler handler) {
            this.webrtcSlowlinkHandler = handler;
            return this;
        }

        Builder serverInfoHandler(final ServerInfoHandler handler) {
            this.serverInfoHandler = handler;
            return this;
        }

        StdResponseHandler build() {
            return new StdResponseHandler(
                    firstNonNull(janusSuccessHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(janusErrorHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(janusAckHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(janusEventHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(webrtcUpHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(webrtcHangupHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(webrtcMediaHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(webrtcSlowlinkHandler, NoopJanusWebrtcHandler.instance()),
                    firstNonNull(serverInfoHandler, NoopJanusWebrtcHandler.instance())
            );
        }
    }

    @Override
    public String toString() {
        return "StdResponseHandler{" +
                "janusAckHandler=" + janusAckHandler +
                '}';
    }
}
