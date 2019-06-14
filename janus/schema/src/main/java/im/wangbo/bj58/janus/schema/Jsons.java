package im.wangbo.bj58.janus.schema;

import com.google.common.primitives.Longs;

import java.util.OptionalLong;

import javax.json.JsonNumber;
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

    public static OptionalLong longValue(final JsonValue value) {
        if (null != value) {
            switch (value.getValueType()) {
                case STRING:
                    final Long l = Longs.tryParse(JsonString.class.cast(value).getString());
                    return null != l ? OptionalLong.of(l) : OptionalLong.empty();
                case NUMBER:
                    final JsonNumber decimal = JsonNumber.class.cast(value);
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
