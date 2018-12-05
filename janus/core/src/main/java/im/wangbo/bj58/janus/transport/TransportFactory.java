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
public interface TransportFactory {
    CompletableFuture<Transport> connect(final URI uri, final ResponseHandler messageHandler);
}
