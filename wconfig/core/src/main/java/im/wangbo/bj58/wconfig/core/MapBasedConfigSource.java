package im.wangbo.bj58.wconfig.core;

import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class MapBasedConfigSource implements ConfigSource {
    private final Map<String, String> backingMap;

    MapBasedConfigSource(final Map<String, String> backingMap) {
        this.backingMap = backingMap;
    }

    @Override
    public JsonObject load() throws ConfigException {
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        backingMap.forEach(builder::add);
        return builder.build();
    }
}
