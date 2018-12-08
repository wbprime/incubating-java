package im.wangbo.bj58.wconfig.core;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.function.Function;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import static im.wangbo.bj58.wconfig.core.JsonUtil.assertJsonByKeys;
import static im.wangbo.bj58.wconfig.core.JsonUtil.assertJsonByPointer;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class Util_transform_Test {
    private final Function<String, String> keyWithPrefix = k -> "value_4_" + k;

    private final String sep = ".";

    @Test
    public void testTransform_emptyKeys() throws Exception {
        final JsonObjectBuilder builder = Util.transform(
                ImmutableList.of(), keyWithPrefix, Splitter.on(sep)
        );

        final JsonObject json = builder.build();
        assertJsonByKeys(json);
    }

    @Test
    public void testTransform_multiKeysWithSep() throws Exception {
        final JsonObjectBuilder builder = Util.transform(
                ImmutableList.of(
                        "key1" + sep + "subkey",
                        "key2" + sep + "subkey",
                        "key3" + sep + "subkey",
                        "key4" + sep + "subkey"
                ), keyWithPrefix, Splitter.on(sep)
        );

        final JsonObject json = builder.build();
        assertJsonByKeys(json, "key1", "key2", "key3", "key4");
        ImmutableList.of("key1", "key2", "key3", "key4")
                .forEach(key -> assertJsonByPointer(json, "/" + key + "/subkey", keyWithPrefix.apply(key + sep + "subkey")));
    }

    @Test
    public void testTransform_multiKeysWithoutSep() throws Exception {
        final JsonObjectBuilder builder = Util.transform(
                ImmutableList.of(
                        "key1",
                        "key2",
                        "key3",
                        "key4"
                ), keyWithPrefix, Splitter.on(sep)
        );

        final JsonObject json = builder.build();
        assertJsonByKeys(json, "key1", "key2", "key3", "key4");
        ImmutableList.of("key1", "key2", "key3", "key4")
                .forEach(key -> assertJsonByPointer(json, "/" + key, keyWithPrefix.apply(key)));
    }

    @Test
    public void testTransform_merge() throws Exception {
        final JsonObjectBuilder builder = Util.transform(
                ImmutableList.of(
                        "key1" + sep + "key11",
                        "key2",
                        "key1" + sep + "key12",
                        "key3",
                        "key1" + sep + "key12" + sep + "key121",
                        "key4"
                ), keyWithPrefix, Splitter.on(sep)
        );

        final JsonObject json = builder.build();
        System.out.println(json);
        assertJsonByKeys(json, "key1", "key2", "key3", "key4");
        ImmutableList.of("key2", "key3", "key4")
                .forEach(key -> assertJsonByPointer(json, "/" + key, keyWithPrefix.apply(key)));
        assertJsonByPointer(json, "/key1/key12/key121", keyWithPrefix.apply("key1.key12.key121"));
        assertJsonByPointer(json, "/key1/key11", keyWithPrefix.apply("key1.key11"));
    }

    @Test
    public void testTransform_merge2() throws Exception {
        final JsonObjectBuilder builder = Util.transform(
                ImmutableList.of(
                        "key1" + sep + "key11",
                        "key2",
                        "key1" + sep + "key12" + sep + "key121",
                        "key3",
                        "key1" + sep + "key12",
                        "key4"
                ), keyWithPrefix, Splitter.on(sep)
        );

        final JsonObject json = builder.build();
        System.out.println(json);
        assertJsonByKeys(json, "key1", "key2", "key3", "key4");
        ImmutableList.of("key2", "key3", "key4")
                .forEach(key -> assertJsonByPointer(json, "/" + key, keyWithPrefix.apply(key)));
        assertJsonByPointer(json, "/key1/key12/key121", keyWithPrefix.apply("key1.key12.key121"));
        assertJsonByPointer(json, "/key1/key12/", keyWithPrefix.apply("key1.key12"));
        assertJsonByPointer(json, "/key1/key11", keyWithPrefix.apply("key1.key11"));
    }
}