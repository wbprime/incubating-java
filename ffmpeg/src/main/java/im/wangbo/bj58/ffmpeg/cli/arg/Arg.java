package im.wangbo.bj58.ffmpeg.cli.arg;

import java.util.Optional;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-10 by Elvis Wang
 */
public interface Arg {

    String name();

    Optional<String> value();

    default String description() {
        return "Arg " + name();
    }
}
