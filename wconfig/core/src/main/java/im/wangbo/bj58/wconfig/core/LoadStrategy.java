package im.wangbo.bj58.wconfig.core;

import java.time.Duration;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public interface LoadStrategy {
    void register(final Reloadable store) throws ConfigException;

    void deregister(final Reloadable store) throws ConfigException;

    static LoadStrategy immediately() {
        return new LoadImmediatelyStrategy();
    }

    static LoadStrategy periodically(final Duration d) {
        return new LoadPeriodicallyStrategy(d);
    }
}
