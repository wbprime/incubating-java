package im.wangbo.bj58.wconfig.core;

import com.google.common.base.Splitter;

import java.util.Properties;

import javax.json.JsonObject;

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
        return Util.transform(
                backingProperties.stringPropertyNames(),
                backingProperties::getProperty,
                Splitter.on('.').omitEmptyStrings().trimResults()
        ).build();
    }
}
