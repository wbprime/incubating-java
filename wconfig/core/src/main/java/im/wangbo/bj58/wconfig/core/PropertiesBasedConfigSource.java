package im.wangbo.bj58.wconfig.core;

import java.util.Properties;

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
public final class PropertiesBasedConfigSource implements ConfigSource {
    private final Properties backingProperties;

    PropertiesBasedConfigSource(final Properties properties) {
        this.backingProperties = properties;
    }

    @Override
    public JsonObject load() throws ConfigException {
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        return builder.build();
    }

    @Override
    public void close() throws ConfigException {
        // Do nothing
    }
}
