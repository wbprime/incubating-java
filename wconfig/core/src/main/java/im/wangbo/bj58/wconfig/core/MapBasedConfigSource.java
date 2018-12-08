package im.wangbo.bj58.wconfig.core;

import com.google.common.base.Splitter;

import java.util.Map;

import javax.json.JsonObject;

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
        return Util.transform(
                backingMap.keySet(),
                backingMap::get,
                Splitter.on('.').omitEmptyStrings().trimResults()
        ).build();
    }
}
