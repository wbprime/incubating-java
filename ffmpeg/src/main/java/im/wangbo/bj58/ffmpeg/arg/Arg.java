package im.wangbo.bj58.ffmpeg.arg;

import im.wangbo.bj58.ffmpeg.common.Value;
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
