package im.wangbo.bj58.wconfig.core;

import com.google.common.collect.Maps;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class Util_collect_Test {
    @Test
    public void testCollect_emptyMap() throws Exception {
        final Map<String, Object> map = Collections.emptyMap();

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        Util.collect(map, builder);

        final JsonObject json = builder.build();
        Assertions.assertThat(json).isEmpty();
    }

    private void assertJson(final JsonObject json, final Predicate<JsonObject> test) {
        final boolean b = test.test(json);
        Assertions.assertThat(b).isTrue();
    }

    private void assertJsonByKeys(final JsonObject json, final String ...keys) {
        assertJson(json, jsonObject -> {
            for (String key : keys) {
                if (! jsonObject.containsKey(key)) return false;
            }

            return jsonObject.size() == keys.length;
        });
    }

    private void assertJsonByPointer(final JsonObject json, final String pointer, final String value) {
        assertJson(json, jsonObject -> {
            final JsonValue v = jsonObject.getValue(pointer);
            if (null == v) return false;
            if (! v.getValueType().equals(JsonValue.ValueType.STRING)) return false;

            final String str = JsonString.class.cast(v).getString();
            return value.equals(str);
        });
    }

    @Test
    public void testCollect_level1Map() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        Util.collect(map, builder);

        final JsonObject json = builder.build();
        assertJsonByKeys(json, "key1", "key2");
        assertJsonByPointer(json, "/key1", "value1");
        assertJsonByPointer(json, "/key2", "value2");
    }

    @Test
    public void testCollect_level2Map() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        {
            final Map<String, Object> map1 = Maps.newHashMap();
            map1.put("key31", "value31");
            map1.put("key32", "value32");
            map.put("key3", map1);
        }

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        Util.collect(map, builder);

        final JsonObject json = builder.build();
        Assertions.assertThat(json).isNotEmpty()
                .containsKeys("key1", "key2", "key3")
                .hasSize(3);
        assertJsonByPointer(json, "/key1", "value1");
        assertJsonByPointer(json, "/key2", "value2");
        assertJsonByPointer(json, "/key3/key31", "value31");
        assertJsonByPointer(json, "/key3/key32", "value32");
    }

    @Test
    public void testCollect_level3Map() throws Exception {
        final Map<String, Object> map = Maps.newHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        {
            final Map<String, Object> map1 = Maps.newHashMap();
            map1.put("key31", "value31");
            map1.put("key32", "value32");
            {
                final Map<String, Object> map2 = Maps.newHashMap();
                map2.put("key331", "value331");
                map2.put("key332", "value332");
                map1.put("key33", map2);
            }
            map.put("key3", map1);
        }

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        Util.collect(map, builder);

        final JsonObject json = builder.build();
        Assertions.assertThat(json).isNotEmpty()
                .containsKeys("key1", "key2", "key3")
                .hasSize(3);
        assertJsonByPointer(json, "/key1", "value1");
        assertJsonByPointer(json, "/key2", "value2");
        assertJsonByPointer(json, "/key3/key31", "value31");
        assertJsonByPointer(json, "/key3/key32", "value32");
    }
}