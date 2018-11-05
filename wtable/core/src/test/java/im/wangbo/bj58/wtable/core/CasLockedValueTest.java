package im.wangbo.bj58.wtable.core;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class CasLockedValueTest {

    private Random random;

    @Before
    public void setUp() throws Exception {
        random = new Random();
    }

    @Test
    public void test_noValue() {
        final int cas = random.nextInt(Integer.MAX_VALUE);

        final CasLockedValue value = CasLockedValue.create(CasStamp.of(cas));

        assertThat(value).isNotNull();
        assertThat(value.casStamp().cas()).isEqualTo(cas);
        assertThat(value.value()).isEmpty();
    }

    @Test
    public void test_hasValue() {
        final String v = String.valueOf(random.nextLong());
        final int cas = random.nextInt(Integer.MAX_VALUE);

        final CasLockedValue value = CasLockedValue.create(
                Wtables.value(v), CasStamp.of(cas));

        assertThat(value).isNotNull();
        assertThat(value.casStamp().cas()).isEqualTo(cas);
        assertThat(value.value()).isPresent();
        assertThat(value.value().get().score()).isEqualTo(0L);
        assertThat(value.value().get().ttl()).isEqualTo(Duration.ofSeconds(0));
        assertThat(value.value().get().value()).isEqualTo(v.getBytes(StandardCharsets.UTF_8));
    }
}