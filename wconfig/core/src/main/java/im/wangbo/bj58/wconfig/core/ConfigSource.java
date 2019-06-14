package im.wangbo.bj58.wconfig.core;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface ConfigSource extends AutoCloseable {
    JsonObject load() throws ConfigException;

    default void open() throws ConfigException {
        // Do nothing
    }

    @Override
    default void close() throws ConfigException {
        // Do nothing
    }
}
