package im.wangbo.bj58.janus.schema;

import com.google.common.primitives.Longs;

import java.util.OptionalLong;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class Jsons {
    private Jsons() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static OptionalLong longValue(final JsonObject json, final String key) {
        final JsonValue v = json.get(key);
        if (null != v) {
            switch (v.getValueType()) {
                case STRING:
                    final Long l = Longs.tryParse(JsonString.class.cast(v).getString());
                    return null != l ? OptionalLong.of(l) : OptionalLong.empty();
                case NUMBER:
                    final JsonNumber decimal = JsonNumber.class.cast(v);
                    return decimal.isIntegral() ?
                            OptionalLong.of(decimal.longValue()) : OptionalLong.empty();
                default:
//                case ARRAY:
//                case OBJECT:
//                case TRUE:
//                case FALSE:
//                case NULL:
                    return OptionalLong.empty();
            }
        } else {
            return OptionalLong.empty();
        }
    }
}
