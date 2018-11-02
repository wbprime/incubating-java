package im.wangbo.bj58.incubating.wmonitor.api;

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

    public static IntConsumer increasing(final WmonitorStub stub, final int attr) {
        return n -> stub.increaseBy(attr, n);
    }

    public static IntConsumer increasing(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.increaseBy(attr, n));
    }

    public static IntConsumer averaging(final WmonitorStub stub, final int attr) {
        return n -> stub.average(attr, n);
    }

    public static IntConsumer averaging(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.average(attr, n));
    }

    public static BiConsumer<Integer, Integer> averagingBy(final WmonitorStub stub, final int attr) {
        return (value, step) -> stub.averageBy(attr, value, step);
    }

    public static BiConsumer<Integer, Integer> averagingBy(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return (value, step) -> attrs.forEach(attr -> stub.averageBy(attr, value, step));
    }

    public static IntConsumer maxing(final WmonitorStub stub, final int attr) {
        return n -> stub.max(attr, n);
    }

    public static IntConsumer maxing(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.max(attr, n));
    }

    public static IntConsumer mining(final WmonitorStub stub, final int attr) {
        return n -> stub.min(attr, n);
    }

    public static IntConsumer mining(final WmonitorStub stub, final Iterable<Integer> attrs) {
        return n -> attrs.forEach(attr -> stub.min(attr, n));
    }
}
