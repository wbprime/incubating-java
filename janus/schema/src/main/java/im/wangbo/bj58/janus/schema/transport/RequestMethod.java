package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
public abstract class RequestMethod {
    public static final String SERVER_INFO= "info";
    public static final String CREATE_SESSION= "create";
    public static final String DESTROY_SESSION= "destroy";
    public static final String ATTACH_PLUGIN= "attach";
    public static final String DETACH_PLUGIN= "detach";
    public static final String HANGUP_PLUGIN= "hangup";
    public static final String SEND_MESSAGE= "message";
    public static final String TRICKLE= "trickle";

    public abstract String method();

    public static RequestMethod serverInfo() { return of(SERVER_INFO);}
    public static RequestMethod createSession() { return of(CREATE_SESSION);}
    public static RequestMethod destroySession() { return of(DESTROY_SESSION);}
    public static RequestMethod attachPlugin() { return of(ATTACH_PLUGIN);}
    public static RequestMethod detachPlugin() { return of(DETACH_PLUGIN);}
    public static RequestMethod hangupPlugin() { return of(HANGUP_PLUGIN);}
    public static RequestMethod sendMessage() { return of(SEND_MESSAGE);}
    public static RequestMethod trickle() { return of(TRICKLE);}

    public static RequestMethod of(final String m) {
        return new AutoValue_RequestMethod(m);
    }
}
