package im.wangbo.bj58.incubating.janus.transport;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class UnsupportedSchemaTransport implements Transport {
    private final URI uri;

    public UnsupportedSchemaTransport(final URI uri) {
        this.uri = uri;
    }

    private <T> T ex() {
        throw new UnsupportedOperationException("Unsupported uri schema: \"" + uri + "\"");
    }

    @Override
    public CompletableFuture<Void> connect(URI uri, ResponseHandler messageHandler) {
        return ex();
    }

    @Override
    public CompletableFuture<Void> close() {
        return ex();
    }

    @Override
    public CompletableFuture<Void> send(GlobalRequest request) {
        return ex();
    }

    @Override
    public CompletableFuture<Void> send(SessionRequest request) {
        return ex();
    }

    @Override
    public CompletableFuture<Void> send(PluginHandleRequest request) {
        return ex();
    }
}
