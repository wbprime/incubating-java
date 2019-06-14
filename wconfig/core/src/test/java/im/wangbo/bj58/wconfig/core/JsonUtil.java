package im.wangbo.bj58.wconfig.core;

import org.assertj.core.api.Assertions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class JsonUtil {
    static void assertJsonByKeys(final JsonObject json, final String... keys) {
        Assertions.<JsonObject>assertThat(json).matches(
                js -> {
                    for (String key : keys) {
                        if (!js.containsKey(key)) return false;
                    }

                    return js.size() == keys.length;
                }
        );
    }

    static void assertJsonByPointer(final JsonObject json, final String pointer, final String value) {
        Assertions.<JsonObject>assertThat(json).matches(
                js -> {
                    final JsonValue v = Json.createPointer(pointer).getValue(js);
                    if (null == v) return false;
                    if (!v.getValueType().equals(JsonValue.ValueType.STRING)) return false;

                    final String str = JsonString.class.cast(v).getString();
                    return value.equals(str);
                }
        );
    }
}
