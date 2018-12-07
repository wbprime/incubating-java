package im.wangbo.bj58.wconfig.core;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
class StdConfigRetriever implements ConfigRetriever, Reloadable {
    private final ConfigSource source;

    private LoadStrategy strategy = new LoadNoopStrategy();
    private Iterable<ConfigChangeHandler> handlers = Collections.emptyList();

    private final AtomicReference<JsonObject> cachedConf = new AtomicReference<>(JsonObject.EMPTY_JSON_OBJECT);

    StdConfigRetriever(final ConfigSource source) {
        this.source = source;
    }

    @Override
    public void reload() throws ConfigException {
        final JsonObject json = source.load();
        final JsonObject oldJson = cachedConf.getAndSet(json);

        if (null != handlers) {
            for (ConfigChangeHandler handler : handlers) {
                handler.onChanged(oldJson, json);
            }
        }
    }

    @Override
    public ConfigRetriever retrieve(
            final LoadStrategy strategy,
            final Iterable<ConfigChangeHandler> handlers
    ) throws ConfigException {
        this.handlers = ImmutableList.copyOf(handlers);

        strategy.register(this);

        return this;
    }

    @Override
    public JsonObject cachedConfig() {
        return cachedConf.get();
    }

    @Override
    public void close() throws ConfigException {
        if (null != strategy) {
            strategy.deregister(this);
        }
        source.close();
    }
}
