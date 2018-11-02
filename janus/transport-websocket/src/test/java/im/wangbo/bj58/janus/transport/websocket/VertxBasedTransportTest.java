package im.wangbo.bj58.janus.transport.websocket;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.util.Random;

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
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@Ignore
@RunWith(VertxUnitRunner.class)
public class VertxBasedTransportTest {

    private static class JanusServer extends AbstractVerticle {
        private final URI uri;

        JanusServer(final URI uri) {
            this.uri = uri;
        }

        @Override
        public void start(Future<Void> startFuture) throws Exception {
            vertx.createHttpServer()
                    .websocketHandler(
                            ws -> {
                                if (ws.path().equals("/")) {
                                    ws.handler(h -> System.out.println(h));
                                } else {
                                    ws.reject();
                                }
                            }
                    ).listen(
                    uri.getPort(), uri.getHost(),
                    as -> {
                        if (as.succeeded()) startFuture.complete();
                        else startFuture.fail(as.cause());
                    }
            );
        }
    }

    private URI websocketUri;

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    @Before
    public void setUp() throws Exception {
        final Random random = new Random();
        final int port = random.nextInt(10000) + 10000;

        final String host = "127.0.0.1";

        websocketUri = URI.create("ws://" + host + ":" + port);
    }

    @Test
    public void test(final TestContext ctx) throws Exception {
        final AbstractVerticle verticle = new JanusServer(websocketUri);
        rule.vertx().deployVerticle(verticle, id -> {
                    final Transport transport = VertxBasedTransport.create(websocketUri);

                    transport.connect(websocketUri, new ResponseHandler() {
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
                    });
                }
        );

        Thread.sleep(5000L);
    }
}