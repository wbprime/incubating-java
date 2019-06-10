package im.wangbo.bj58.ffmpeg.arg;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class ValueTest {
    @Test
    void offsetDatetime() {
        final OffsetDateTime dt = OffsetDateTime.now();
        final Value v = Value.datetime(dt);

        final String expected = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS'Z'")
                .format(dt.withOffsetSameInstant(ZoneOffset.UTC));
        Assertions.assertThat(v.encode()).isEqualTo(expected);
    }
    @Test
    void localDatetime() {
        final LocalDateTime dt = LocalDateTime.now();
        final Value v = Value.datetime(dt);

        final String expected = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS").format(dt);
        Assertions.assertThat(v.encode()).isEqualTo(expected);
    }
}