package im.wangbo.bj58.janus.schema;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public enum RequestMethod {
    SERVER_INFO("info"),
    CREATE_SESSION("create"),
    DESTROY_SESSION("destroy"),
    ATTACH_PLUGIN("attach"),
    DETACH_PLUGIN("detach"),
    HANGUP_PLUGIN("hangup"),
    PLUGIN_MESSAGE("message"),
    TRICKLE("trickle");

    private final String type;

    RequestMethod(final String v) {
        this.type = v;
    }

    public final String method() {
        return type;
    }
}
