package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Arg {
    ArgSpec spec();

    Optional<Value> value();
}
