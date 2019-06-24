package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class Filter implements Node {
    public abstract FilterSpec spec();

    public abstract ImmutableList<FilterArg> args();

    interface Builder {
        Filter build();
    }
}
