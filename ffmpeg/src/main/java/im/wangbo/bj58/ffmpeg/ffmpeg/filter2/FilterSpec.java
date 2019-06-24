package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import java.util.List;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
public abstract class FilterSpec {
    public abstract String typeId();

    public abstract List<FilterArg> declaredArgs();

    abstract int inComings();

    int minInComings() {
        return inComings();
    }

    abstract int outGoings();
}
