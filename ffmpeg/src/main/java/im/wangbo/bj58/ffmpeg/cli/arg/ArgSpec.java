package im.wangbo.bj58.ffmpeg.cli.arg;

import java.util.List;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
public interface ArgSpec {
    String name();

    String description();

    List<ValueValidator> validators();
}
