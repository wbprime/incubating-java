package im.wangbo.bj58.ffmpeg.cli.arg;

import im.wangbo.bj58.ffmpeg.common.Value;
import java.util.StringJoiner;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-10 by Elvis Wang
 */
public abstract class ArgSpec<T extends Value> {
    public abstract String name();

    public abstract String description();

    public final Arg createArg(final T value) {
        return Arg.create(name(), value.stringify());
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ", ArgSpec.class.getSimpleName() + "[", "]")
            .add("name='" + name() + "'")
            .add("description='" + description() + "'")
            .toString();
    }
}
