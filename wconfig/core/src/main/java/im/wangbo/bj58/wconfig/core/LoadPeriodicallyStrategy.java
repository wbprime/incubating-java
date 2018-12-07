package im.wangbo.bj58.wconfig.core;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class LoadPeriodicallyStrategy implements LoadStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(LoadPeriodicallyStrategy.class);

    private final Timer timer = new Timer(true);
    private final Map<Reloadable, TimerTask> tasks = Maps.newConcurrentMap();

    private final Duration interval;

    public LoadPeriodicallyStrategy(final Duration interval) {
        this.interval = interval;
    }

    @Override
    public void register(final Reloadable store) throws ConfigException {
        LOG.debug("Registering on {}: {}", getClass(), store);

        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    store.reload();
                } catch (Exception ex) {
                    LOG.warn("Periodically reload {} failed, scheduled a retry after {}", store, interval, ex);
                }
            }
        };

        store.reload();

        tasks.put(store, task);
        timer.schedule(task, interval.toMillis(), interval.toMillis());
    }

    @Override
    public void deregister(final Reloadable store) {
        LOG.debug("Deregistering on {}: {}", getClass(), store);

        final TimerTask task = tasks.remove(store);
        if (null != task) task.cancel();
    }
}
