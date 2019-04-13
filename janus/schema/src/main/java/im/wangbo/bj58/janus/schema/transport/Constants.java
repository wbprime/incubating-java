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

    static OptionalLong sessionId(final JsonObject json, final String key) {
        return Jsons.longValue(json, key);
    }

    static OptionalLong pluginHandleId(final JsonObject json, final String key) {
        return Jsons.longValue(json, key);
    }
}
