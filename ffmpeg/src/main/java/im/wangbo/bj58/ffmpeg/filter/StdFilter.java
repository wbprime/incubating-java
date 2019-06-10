package im.wangbo.bj58.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class StdFilter implements Filter {
    @Override
    public abstract String typeId();

    @Override
    public abstract ImmutableList<FilterPad> input();

    @Override
    public abstract ImmutableList<FilterPad> output();

    @Override
    public abstract List<Arg> args();

    public static Builder builder(final String type) {
        return new AutoValue_StdFilter.Builder().typeId(type);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder typeId(String typeId);

        public abstract Builder input(ImmutableList<FilterPad> input);

        public abstract Builder output(ImmutableList<FilterPad> output);

        public abstract Builder args(List<Arg> args);

        public abstract StdFilter build();
    }
}
