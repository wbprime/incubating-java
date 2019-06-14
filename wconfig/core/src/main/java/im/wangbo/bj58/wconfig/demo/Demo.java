package im.wangbo.bj58.wconfig.demo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

import im.wangbo.bj58.wconfig.core.ConfigRetriever;
import im.wangbo.bj58.wconfig.core.ConfigSource;
import im.wangbo.bj58.wconfig.core.LoadStrategy;
import im.wangbo.bj58.wconfig.core.MapBasedConfigSource;
import im.wangbo.bj58.wconfig.core.PropertiesBasedConfigSource;
import im.wangbo.bj58.wconfig.core.StdConfigRetriever;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class Demo {
    private static void prettyPrint(final JsonObject json) {
        final JsonWriter writer = Json.createWriterFactory(
                ImmutableMap.of(JsonGenerator.PRETTY_PRINTING, true)
        ).createWriter(System.out);
        writer.write(json);
    }

    public static void main(String[] args) throws Exception {
        {
            final ConfigSource source = PropertiesBasedConfigSource.of(
                    System.getProperties(), "."
            );
            final ConfigRetriever retriever = StdConfigRetriever.create(source);
            final JsonObject config = retriever.retrieve(LoadStrategy.immediately(), ImmutableList.of())
                    .cachedConfig();
            prettyPrint(config);
        }
        {
            final CountDownLatch latch = new CountDownLatch(1);
            final Map<String, String> data = Maps.newHashMap();

            final ConfigSource source = MapBasedConfigSource.of(data, "_");
            final ConfigRetriever retriever = StdConfigRetriever.create(source);
            retriever.retrieve(
                    LoadStrategy.periodically(Duration.ofSeconds(1)),
                    ImmutableList.of(
                            (oldJson, newJson) -> {
                                prettyPrint(newJson);
                                if (newJson.size() > 100) {
                                    latch.countDown();
                                } else {
                                    final Random random = new Random();
                                    final int len = 1 + random.nextInt(5);
                                    final List<String> list = Lists.newArrayListWithCapacity(len);
                                    for (int i = 0; i < len; i++) {
                                        list.add("k" + random.nextInt(10));
                                    }
                                    data.put(String.join("_", list), "" + Math.random());
                                }
                            }
                    )
            ).cachedConfig();

            latch.await(10L, TimeUnit.SECONDS);
        }
    }
}
