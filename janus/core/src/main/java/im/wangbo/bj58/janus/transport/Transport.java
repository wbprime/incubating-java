package im.wangbo.bj58.janus.transport;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface Transport {
//    CompletableFuture<Void> connect(final URI uri, final ResponseHandler messageHandler);

    CompletableFuture<Void> close();

    CompletableFuture<Void> send(final GlobalRequest request);

    CompletableFuture<Void> send(final SessionRequest request);

    CompletableFuture<Void> send(final PluginHandleRequest request);
}
