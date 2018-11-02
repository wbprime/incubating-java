package im.wangbo.bj58.janus.protocol;

import im.wangbo.bj58.janus.PluginHandle;
import im.wangbo.bj58.janus.Session;
import im.wangbo.bj58.janus.transport.JanusAckResponse;
import im.wangbo.bj58.janus.transport.JanusErrorResponse;
import im.wangbo.bj58.janus.transport.JanusSuccessResponse;
import im.wangbo.bj58.janus.transport.ServerInfoResponse;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class NoopJanusWebrtcHandler implements ServerInfoHandler,
        JanusSuccessHandler, JanusErrorHandler, JanusAckHandler, JanusEventHandler,
        WebrtcUpHandler, WebrtcHangupHandler, WebrtcMediaHandler, WebrtcSlowlinkHandler {

    private static final class Holder {
        private static final NoopJanusWebrtcHandler INSTANCE = new NoopJanusWebrtcHandler();

        static NoopJanusWebrtcHandler instance() {
            return INSTANCE;
        }
    }

    private NoopJanusWebrtcHandler() { }

    static NoopJanusWebrtcHandler instance() {
        return Holder.instance();
    }

    @Override
    public void onServerInfo(final ServerInfoResponse response) {

    }

    @Override
    public void onSuccess(final JanusSuccessResponse response) {
        // Noop
    }

    @Override
    public void onAck(final JanusAckResponse response) {
        // Noop
    }

    @Override
    public void onError(final JanusErrorResponse response) {
        // Noop
    }

    @Override
    public void onEvent() {
        // Noop
    }

    @Override
    public void onHangup(Session session, PluginHandle handle, String reason) {
        // Noop
    }

    @Override
    public void onMedia(Session session, PluginHandle handle, String type, boolean receiving) {
        // Noop
    }

    @Override
    public void onSlowLink(Session session, PluginHandle handle, boolean uplink, int nacks) {
        // Noop
    }

    @Override
    public void onWebrtcUp(Session session, PluginHandle handle) {
        // Noop
    }
}
