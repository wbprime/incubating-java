package im.wangbo.bj58.janus.schema.transport;

import java.util.OptionalLong;

import javax.json.JsonObject;

import im.wangbo.bj58.janus.schema.Jsons;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
final class Constants {
    private Constants() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    static final String REQ_FIELD_REQUEST_TYPE = "janus";

    static final String REQ_REQ_TP_SERVER_INFO = "info";
    static final String REQ_REQ_TP_CREATE_SESSION = "create";
    static final String REQ_REQ_TP_DESTROY_SESSION = "destroy";
    static final String REQ_REQ_TP_ATTACH_PLUGIN = "attach";
    static final String REQ_REQ_TP_DETACH_PLUGIN = "detach";
    static final String REQ_REQ_TP_HANGUP_PLUGIN = "hangup";
    static final String REQ_REQ_TP_PLUGIN_MESSAGE = "message";
    static final String REQ_REQ_TP_TRICKLE = "trickle";

    static OptionalLong sessionId(final JsonObject json, final String key) {
        return Jsons.longValue(json, key);
    }

    static OptionalLong pluginHandleId(final JsonObject json, final String key) {
        return Jsons.longValue(json, key);
    }
}
