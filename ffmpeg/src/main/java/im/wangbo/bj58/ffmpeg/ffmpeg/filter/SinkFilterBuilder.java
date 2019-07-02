package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class SinkFilterBuilder implements Filter.Builder {
    @Override
    public Filter build(final String name) {
        return Filter.of(
                name, FilterSpec.tmp(), ImmutableList.of()
        );
    }

    public static SinkFilterBuilder builder() {
        return new AutoValue_SinkFilterBuilder();
    }
}

