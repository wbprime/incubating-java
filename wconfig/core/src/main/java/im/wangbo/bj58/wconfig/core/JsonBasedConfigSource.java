package im.wangbo.bj58.wconfig.core;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class JsonBasedConfigSource implements ConfigSource {
    private final JsonObject backingJson;

    public static JsonBasedConfigSource of(final JsonObject json) {
        return new JsonBasedConfigSource(json);
    }

    private JsonBasedConfigSource(final JsonObject json) {
        this.backingJson = json;
    }

    @Override
    public JsonObject load() throws ConfigException {
        return backingJson;
    }
}
