package im.wangbo.bj58.incubating.api;

/**
 * Wmonitor client stub.
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang
 */
public interface WmonitorStub {
    /**
     * Refer to {@code WMonitor#sum(attr, 1)}
     */
    default void increase(final int attr) {
        increaseBy(attr, 1);
    }
    /**
     * Refer to {@code WMonitor#sum(attr, value)}
     */
    void increaseBy(final int attr, final int step);

    /**
     * Refer to {@code WMonitor#average(attr, value, 1)}
     */
    default void average(final int attr, final int value) {
        averageBy(attr, value, 1);
    }
    /**
     * Refer to {@code WMonitor#average(attr, value, step)}
     */
    void averageBy(final int attr, final int value, int step);

    /**
     * Refer to {@code WMonitor#max(attr, value)}
     */
    void max(final int attr, final int value);
    /**
     * Refer to {@code WMonitor#min(attr, value)}
     */
    void min(final int attr, final int value);
}
