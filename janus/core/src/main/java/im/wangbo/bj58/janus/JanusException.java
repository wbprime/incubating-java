package im.wangbo.bj58.janus;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
class JanusException extends RuntimeException {
    JanusException(final String m) {
        super(m, null);
    }

    JanusException(final String m, final Throwable t) {
        super(m, t);
    }
}
