package im.wangbo.bj58.wconfig.core;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
final class LoadNoopStrategy implements LoadStrategy {
    @Override
    public void register(final Reloadable store) {
        // Do nothing
    }

    @Override
    public void deregister(final Reloadable store) {
        // Do nothing
    }
}
