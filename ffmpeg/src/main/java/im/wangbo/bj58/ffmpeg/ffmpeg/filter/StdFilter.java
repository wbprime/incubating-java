package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
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
    public abstract ImmutableList<FilterPad> inputs();

    @Override
    public abstract ImmutableList<FilterPad> outputs();

    @Override
    public abstract ImmutableList<FilterArg> args();

    public static Builder builder(final String type) {
        return new AutoValue_StdFilter.Builder().typeId(type);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder typeId(String typeId);

        abstract ImmutableList.Builder<FilterPad> inputsBuilder();
        public final Builder addInput(final FilterPad input) {
            inputsBuilder().add(input);
            return this;
        }

        abstract ImmutableList.Builder<FilterPad> outputsBuilder();
        public final Builder addOutput(FilterPad output) {
            outputsBuilder().add(output);
            return this;
        }

        public abstract Builder args(ImmutableList<FilterArg> args);

        abstract ImmutableList.Builder<FilterArg> argsBuilder();

        public final Builder addArg(final FilterArg arg) {
            argsBuilder().add(arg);
            return this;
        }

        public abstract StdFilter build();
    }
}
