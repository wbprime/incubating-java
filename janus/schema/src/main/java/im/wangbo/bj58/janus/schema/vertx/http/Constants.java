package im.wangbo.bj58.janus.schema.vertx.http;

import java.util.OptionalLong;

import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.json.JsonValue;

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
    static final String REQ_FIELD_TRANSACTION = "transaction";
    static final String REQ_FIELD_DATA = "data";
    static final String REQ_FIELD_DATA_SESSION_ID = "id";
    static final String REQ_FIELD_DATA_PLUGINHANDLE_ID = "id";

    // JSON Pointer <a href="http://tools.ietf.org/html/rfc6901">RFC 6901</a>
    private static final String POINTER_DATA_SESSION_ID = "/" + REQ_FIELD_DATA + "/" + REQ_FIELD_DATA_SESSION_ID;
    private static final String POINTER_DATA_PLUGINHANDLE_ID = "/" + REQ_FIELD_DATA + "/" + REQ_FIELD_DATA_PLUGINHANDLE_ID;

    /**
     * @param json response body in json
     * @return session id extracted from response json
     */
    static OptionalLong sessionId(final JsonObject json) {
        final JsonPointer sessionIdPointer = Json.createPointer(Constants.POINTER_DATA_SESSION_ID);
        try {
            final JsonValue value = sessionIdPointer.getValue(json);
            return Jsons.longValue(value);
        } catch (JsonException ex) {
            // TODO handle exception
            return OptionalLong.empty();
        }
    }

    /**
     * @param json response body in json
     * @return plugin handle id extracted from response json
     */
    static OptionalLong pluginHandleId(final JsonObject json) {
        final JsonPointer sessionIdPointer = Json.createPointer(Constants.POINTER_DATA_PLUGINHANDLE_ID);
        try {
            final JsonValue value = sessionIdPointer.getValue(json);
            return Jsons.longValue(value);
        } catch (JsonException ex) {
            // TODO handle exception
            return OptionalLong.empty();
        }
    }
}
