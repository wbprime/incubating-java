package im.wangbo.bj58.ffmpeg.arg;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Arg {
    String argName();

    Optional<String> argValue();

    default String description() {
        return "";
    }
}
