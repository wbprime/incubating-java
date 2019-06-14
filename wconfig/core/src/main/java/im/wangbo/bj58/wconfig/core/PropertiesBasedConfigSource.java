package im.wangbo.bj58.wconfig.core;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.Optional;
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
    private final Optional<String> sep;

    public static PropertiesBasedConfigSource systemProperties() {
        return of(System.getProperties());
    }

    public static PropertiesBasedConfigSource systemProperties(final String sep) {
        return of(System.getProperties(), sep);
    }

    public static PropertiesBasedConfigSource of(final Properties p) {
        return new PropertiesBasedConfigSource(p, Optional.empty());
    }

    public static PropertiesBasedConfigSource of(
            final Properties p, final String sep
    ) {
        return new PropertiesBasedConfigSource(p, Optional.of(sep));
    }

    private PropertiesBasedConfigSource(
            final Properties properties, final Optional<String> sep
    ) {
        this.backingProperties = properties;
        this.sep = sep;
    }

    @Override
    public JsonObject load() throws ConfigException {
        return Util.transform(
                backingProperties.stringPropertyNames(),
                backingProperties::getProperty,
                sep.map(s -> Splitter.on(s).omitEmptyStrings().trimResults())
                        .orElse(Splitter.on(CharMatcher.none()))
        ).build();
    }
}
