package im.wangbo.bj58.janus.transport.websocket;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
@AutoValue
abstract class TypeResp {
    static final String TYPE_ACK           = "ack";
    static final String TYPE_ERROR         = "error";
    static final String TYPE_EVENT         = "event";
    static final String TYPE_HANGUP        = "hangup";
    static final String TYPE_MEDIA         = "media";
    static final String TYPE_SERVER_INFO   = "server_info";
    static final String TYPE_SLOWLINK      = "slowlink";
    static final String TYPE_SUCCESS       = "success";
    static final String TYPE_WEBRTCUP      = "webrtcup";

    abstract String getResponseType();

    static TypeResp create(String type) {
        return new AutoValue_TypeResp(type);
    }

    static TypeResp fromJson(final JsonObject obj) {
        return TypeResp.create(obj.getString("janus"));
    }
}
