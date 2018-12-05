package im.wangbo.bj58.janus.protocol;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;

import im.wangbo.bj58.janus.ServerInfo;
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
final class RespHandler implements ResponseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RespHandler.class);

    private ConcurrentMap<String, CompletableFuture<ServerInfo>> serverInfoHanders = Maps.newConcurrentMap();

    void register(final Transaction t, final CompletableFuture<ServerInfo> future) {
        final String tid = t.id();
        final CompletableFuture<ServerInfo> f = serverInfoHanders.putIfAbsent(tid, future);
        if (null != f) {
            LOGGER.warn("A handler for transaction \"{}\" already exists", tid);
        }
    }

    @Override
    public void handle(JanusSuccessResponse response) {
        LOGGER.info("success async response: {}", response);
    }

    @Override
    public void handle(JanusAckResponse response) {
        LOGGER.debug("ack async response: {}", response);
    }

    @Override
    public void handle(JanusErrorResponse response) {
        LOGGER.debug("error async response: {}", response);
    }

    @Override
    public void handle(JanusEventResponse response) {
        LOGGER.info("event async response: {}", response);
    }

    @Override
    public void handle(WebrtcHangupResponse response) {
        LOGGER.info("hangup async response: {}", response);
    }

    @Override
    public void handle(WebrtcMediaResponse response) {
        LOGGER.info("media async response: {}", response);
    }

    @Override
    public void handle(WebrtcUpResponse response) {
        LOGGER.info("webrtcup async response: {}", response);
    }

    @Override
    public void handle(WebrtcSlowlinkResponse response) {
        LOGGER.info("slowlink async response: {}", response);
    }

    @Override
    public void handle(final ServerInfoResponse response) {
        LOGGER.debug("ServerInfo async response: {}", response);
        final String tid = response.transaction().id();
        final CompletableFuture<ServerInfo> future = serverInfoHanders.remove(tid);
        if (null != future) {
            future.complete(response.serverInfo());
        } else {
            LOGGER.warn("No ServerInfo handler registered for transaction \"{}\"", tid);
        }
    }

    @Override
    public void handle(UnknownResponse response) {
        LOGGER.info("unknown type response: {}", response);
    }

    @Override
    public void handle(InvalidResponse response) {
        LOGGER.info("Invalid async response: {}", response);
    }
}
