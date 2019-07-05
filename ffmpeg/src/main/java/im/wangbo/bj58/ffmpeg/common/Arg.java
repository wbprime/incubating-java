package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Arg {
    ArgSpec spec();

    default String name() {
        return spec().name();
    }

    Optional<Value> value();
}
