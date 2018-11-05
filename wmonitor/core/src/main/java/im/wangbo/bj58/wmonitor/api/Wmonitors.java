package im.wangbo.bj58.wmonitor.api;

import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public final class Wmonitors {
    private Wmonitors() {
        throw new AssertionError("Construction forbidden");
    }

    /**
     * Wrap a {@link WmonitorStub#increaseBy(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attr attribute field
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#increaseBy(int, int)}
     */
    public static IntConsumer increasing(final WmonitorStub stub, final int attr) {
        return n -> stub.increaseBy(attr, n);
    }

    /**
     * Wrap a {@link WmonitorStub#increaseBy(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attrs attribute fields
     * @return an {@link IntConsumer} instance performing multi {@link WmonitorStub#increaseBy(int, int)}
     */
    public static IntConsumer increasing(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.increaseBy(attr, n));
    }

    /**
     * Wrap a {@link WmonitorStub#average(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attr attribute field
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#average(int, int)}
     */
    public static IntConsumer averaging(final WmonitorStub stub, final int attr) {
        return n -> stub.average(attr, n);
    }

    /**
     * Wrap a {@link WmonitorStub#average(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attrs attribute fields
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#average(int, int)}
     */
    public static IntConsumer averaging(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.average(attr, n));
    }

    /**
     * Wrap a {@link WmonitorStub#averageBy(int, int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attr attribute field
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#averageBy(int, int, int)}
     */
    public static BiConsumer<Integer, Integer> averagingBy(final WmonitorStub stub, final int attr) {
        return (value, step) -> stub.averageBy(attr, value, step);
    }

    /**
     * Wrap a {@link WmonitorStub#averageBy(int, int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attrs attribute fields
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#averageBy(int, int, int)}
     */
    public static BiConsumer<Integer, Integer> averagingBy(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return (value, step) -> attrs.forEach(attr -> stub.averageBy(attr, value, step));
    }

    /**
     * Wrap a {@link WmonitorStub#max(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attr attribute field
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#max(int, int)}
     */
    public static IntConsumer maxing(final WmonitorStub stub, final int attr) {
        return n -> stub.max(attr, n);
    }

    /**
     * Wrap a {@link WmonitorStub#max(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attrs attribute fields
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#max(int, int)}
     */
    public static IntConsumer maxing(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.max(attr, n));
    }

    /**
     * Wrap a {@link WmonitorStub#min(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attr attribute field
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#min(int, int)}
     */
    public static IntConsumer mining(final WmonitorStub stub, final int attr) {
        return n -> stub.min(attr, n);
    }

    /**
     * Wrap a {@link WmonitorStub#min(int, int)} operation to be used in functional cases.
     *
     * @param stub a {@link WmonitorStub} instance
     * @param attrs attribute fields
     * @return an {@link IntConsumer} instance performing {@link WmonitorStub#min(int, int)}
     */
    public static IntConsumer mining(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.min(attr, n));
    }
}
