package im.wangbo.bj58.janus.transport;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface ResponseHandler {
    void handle(final JanusSuccessResponse response);
    void handle(final JanusErrorResponse response);
    void handle(final JanusAckResponse response);
    void handle(final JanusEventResponse response);
    void handle(final WebrtcUpResponse response);
    void handle(final WebrtcHangupResponse response);
    void handle(final WebrtcMediaResponse response);
    void handle(final WebrtcSlowlinkResponse response);
    void handle(final ServerInfoResponse response);
    void handle(final UnknownResponse response);
}
