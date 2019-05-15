package im.wangbo.bj58.ffmpeg.filter;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import im.wangbo.bj58.ffmpeg.common.Name;
import im.wangbo.bj58.ffmpeg.common.Pair;
import im.wangbo.bj58.ffmpeg.common.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Filter {
    public abstract String name();

    public abstract ImmutableList<String> inputLabels();

    public abstract ImmutableList<String> outputLabels();

    public abstract ImmutableList<Pair> options();

    static Builder builder(final String name) {
        return new AutoValue_Filter.Builder().name(name);
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder name(String name);

        abstract ImmutableList.Builder<String> inputLabelsBuilder();

        public final Builder addInputLabel(final String v) {
            inputLabelsBuilder().add(v);
            return this;
        }

        abstract ImmutableList.Builder<String> outputLabelsBuilder();

        public final Builder addOutputLabel(final String v) {
            outputLabelsBuilder().add(v);
            return this;
        }

        abstract ImmutableList.Builder<Pair> optionsBuilder();

        public final Builder addOption(final Pair opt) {
            optionsBuilder().add(opt);
            return this;
        }

        public final Builder addOption(final Name k, final Value v) {
            optionsBuilder().add(Pair.of(k, v));
            return this;
        }

        public abstract Filter build();
    }
}
