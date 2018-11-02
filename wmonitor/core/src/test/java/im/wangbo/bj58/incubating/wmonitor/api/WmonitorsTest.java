package im.wangbo.bj58.incubating.wmonitor.api;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class WmonitorsTest {
    private WmonitorStub wmonitorStub;

    private int testingAttr;
    private List<Integer> testingAttrs;

    private int value;
    private int step;

    @Before
    public void setUp() throws Exception {
        wmonitorStub = mock(WmonitorStub.class);

        final Random random = new Random();

        testingAttr = random.nextInt(10000000);

        final int n = random.nextInt(1000);
        testingAttrs = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            testingAttrs.add(testingAttr + i * 2);
        }

        value = random.nextInt();
        step = random.nextInt();
    }

    @Test
    public void increasing_single() {
        final IntConsumer consumer = Wmonitors.increasing(wmonitorStub, testingAttr);

        consumer.accept(value);

        verify(wmonitorStub).increaseBy(testingAttr, value);
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void increasing_multi() {
        final IntConsumer consumer = Wmonitors.increasing(wmonitorStub, testingAttrs);

        consumer.accept(value);

        testingAttrs.forEach(
                i -> verify(wmonitorStub).increaseBy(i, value)
        );
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void averaging_single() {
        final IntConsumer consumer = Wmonitors.averaging(wmonitorStub, testingAttr);

        consumer.accept(value);

        verify(wmonitorStub).average(testingAttr, value);
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void averaging_multi() {
        final IntConsumer consumer = Wmonitors.averaging(wmonitorStub, testingAttrs);

        consumer.accept(value);

        testingAttrs.forEach(
                i -> verify(wmonitorStub).average(i, value)
        );
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void averagingBy_single() {
        final BiConsumer<Integer, Integer> consumer = Wmonitors.averagingBy(wmonitorStub, testingAttr);

        consumer.accept(value, step);

        verify(wmonitorStub).averageBy(testingAttr, value, step);
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void averagingBy_multi() {
        final BiConsumer<Integer, Integer> consumer = Wmonitors.averagingBy(wmonitorStub, testingAttrs);

        consumer.accept(value, step);

        testingAttrs.forEach(
                i -> verify(wmonitorStub).averageBy(i, value, step)
        );
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void mining_single() {
        final IntConsumer consumer = Wmonitors.mining(wmonitorStub, testingAttr);

        consumer.accept(value);

        verify(wmonitorStub).min(testingAttr, value);
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void mining_multi() {
        final IntConsumer consumer = Wmonitors.mining(wmonitorStub, testingAttrs);

        consumer.accept(value);

        testingAttrs.forEach(
                i -> verify(wmonitorStub).min(i, value)
        );
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void maxing_single() {
        final IntConsumer consumer = Wmonitors.maxing(wmonitorStub, testingAttr);

        consumer.accept(value);

        verify(wmonitorStub).max(testingAttr, value);
        verifyNoMoreInteractions(wmonitorStub);
    }

    @Test
    public void maxing_multi() {
        final IntConsumer consumer = Wmonitors.maxing(wmonitorStub, testingAttrs);

        consumer.accept(value);

        testingAttrs.forEach(
                i -> verify(wmonitorStub).max(i, value)
        );
        verifyNoMoreInteractions(wmonitorStub);
    }
}