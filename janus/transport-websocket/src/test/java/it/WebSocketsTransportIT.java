package it;

import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import im.wangbo.bj58.janus.Transaction;
import im.wangbo.bj58.janus.transport.GlobalRequest;
import im.wangbo.bj58.janus.transport.InvalidResponse;
import im.wangbo.bj58.janus.transport.JanusAckResponse;
import im.wangbo.bj58.janus.transport.JanusErrorResponse;
import im.wangbo.bj58.janus.transport.JanusEventResponse;
import im.wangbo.bj58.janus.transport.JanusSuccessResponse;
import im.wangbo.bj58.janus.transport.ResponseHandler;
import im.wangbo.bj58.janus.transport.ServerInfoResponse;
import im.wangbo.bj58.janus.transport.Transport;
import im.wangbo.bj58.janus.transport.UnknownResponse;
import im.wangbo.bj58.janus.transport.WebrtcHangupResponse;
import im.wangbo.bj58.janus.transport.WebrtcMediaResponse;
import im.wangbo.bj58.janus.transport.WebrtcSlowlinkResponse;
import im.wangbo.bj58.janus.transport.WebrtcUpResponse;
import im.wangbo.bj58.janus.transport.websocket.VertxBasedTransport;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@Ignore
public class WebSocketsTransportIT {
    @Test
    public void test() {
        final Transport transport = VertxBasedTransport.create();

        final URI uri = URI.create("ws://10.9.192.162:8888/janus");

        final CompletableFuture<Void> connect = transport.connect(uri, new ResponseHandler() {
            @Override
            public void handle(JanusSuccessResponse response) {

            }

            @Override
            public void handle(JanusErrorResponse response) {

            }

            @Override
            public void handle(JanusAckResponse response) {

            }

            @Override
            public void handle(JanusEventResponse response) {

            }

            @Override
            public void handle(WebrtcUpResponse response) {

            }

            @Override
            public void handle(WebrtcHangupResponse response) {

            }

            @Override
            public void handle(WebrtcMediaResponse response) {

            }

            @Override
            public void handle(WebrtcSlowlinkResponse response) {

            }

            @Override
            public void handle(ServerInfoResponse response) {

            }

            @Override
            public void handle(UnknownResponse response) {

            }

            @Override
            public void handle(InvalidResponse response) {

            }
        });

        connect.join();

        final CompletableFuture<Void> serverInfo = transport.send(GlobalRequest.builder()
                .request("info")
                .transaction(Transaction.of())
                .build());

        serverInfo.join();
    }
}
