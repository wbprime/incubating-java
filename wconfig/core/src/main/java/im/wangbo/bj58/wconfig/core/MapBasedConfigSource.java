package im.wangbo.bj58.wconfig.core;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.Map;
import java.util.Optional;

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
    private final Optional<String> sep;

    public static MapBasedConfigSource systemEnv() {
        return of(System.getenv());
    }

    public static MapBasedConfigSource of(final String sep) {
        return of(System.getenv(), sep);
    }

    public static MapBasedConfigSource of(final Map<String, String> map) {
        return new MapBasedConfigSource(map, Optional.empty());
    }

    public static MapBasedConfigSource of(final Map<String, String> map, final String sep) {
        return new MapBasedConfigSource(map, Optional.of(sep));
    }

    private MapBasedConfigSource(
            final Map<String, String> backingMap,
            final Optional<String> sep
    ) {
        this.backingMap = backingMap;
        this.sep = sep;
    }

    @Override
    public JsonObject load() throws ConfigException {
        return Util.transform(
                backingMap.keySet(),
                backingMap::get,
                sep.map(s -> Splitter.on(s).omitEmptyStrings().trimResults())
                        .orElse(Splitter.on(CharMatcher.none()))
        ).build();
    }
}
