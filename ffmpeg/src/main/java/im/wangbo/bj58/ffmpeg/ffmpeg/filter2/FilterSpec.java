package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
@AutoValue
public abstract class FilterSpec {

    public abstract String typeId();

    public abstract List<FilterArg> declaredArgs();

    abstract int incomings();

    abstract int outgoings();

    public static FilterSpec tmp() {
        return create("TODO", ImmutableList.of(FilterArg.of("tmpArg", "tmpValue")), 0, 0);
    }

    public static FilterSpec create(String typeId, List<FilterArg> declaredArgs, int incomings,
        int outgoings) {
        return builder()
            .typeId(typeId)
            .declaredArgs(declaredArgs)
            .incomings(incomings)
            .outgoings(outgoings)
            .build();
    }

    public static Builder builder() {
        return new AutoValue_FilterSpec.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder typeId(String typeId);

        public abstract Builder declaredArgs(List<FilterArg> declaredArgs);

        public abstract Builder incomings(int incomings);

        public abstract Builder outgoings(int outgoings);

        public abstract FilterSpec build();
    }
}
