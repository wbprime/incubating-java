package im.wangbo.bj58.janus.protocol;

import im.wangbo.bj58.janus.transport.JanusErrorResponse;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
interface JanusErrorHandler {
    void onError(final JanusErrorResponse response);
}
