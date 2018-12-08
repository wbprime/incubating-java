package im.wangbo.bj58.wconfig.core;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface ConfigRetriever extends AutoCloseable {
    JsonObject cachedConfig();

    ConfigRetriever retrieve(
            final LoadStrategy strategy, final Iterable<ConfigChangeHandler> handlers
    ) throws ConfigException;

    @Override
    void close() throws ConfigException;
}
