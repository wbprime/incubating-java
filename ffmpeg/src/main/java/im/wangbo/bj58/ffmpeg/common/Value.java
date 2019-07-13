package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Value {
    String asString();

    default Optional<String> stringify() {
        return Optional.of(asString());
    }
}
