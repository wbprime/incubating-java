package im.wangbo.bj58.wconfig.core;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.util.function.Function;

import javax.json.JsonObjectBuilder;

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

        final
    }
}