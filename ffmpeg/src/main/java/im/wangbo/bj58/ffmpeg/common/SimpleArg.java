package im.wangbo.bj58.ffmpeg.common;

import com.google.auto.value.AutoValue;
import java.util.Optional;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-03 by Elvis Wang
 */
@AutoValue
abstract class SimpleArg implements Arg {

    @Override
    public abstract ArgSpec spec();

    @Override
    public abstract Optional<Value> value();

    private static SimpleArg create(final ArgSpec spec, final Optional<Value> value) {
        return new AutoValue_SimpleArg(spec, value);
    }

    static SimpleArg of(final String name, final Value v, final String desc) {
        return create(ArgSpec.of(name, desc), Optional.of(v));
    }

    static SimpleArg named(final String name) {
        return create(ArgSpec.of(name, ""), Optional.empty());
    }

    static SimpleArg paired(final String name, final Value v) {
        return create(ArgSpec.of(name, ""), Optional.of(v));
    }

    static SimpleArg paired(final String name, final String v) {
        return create(ArgSpec.of(name, ""), Optional.of(Value.ofString(v)));
    }

    static SimpleArg paired(final String name, final long v) {
        return create(ArgSpec.of(name, ""), Optional.of(Value.ofLong(v)));
    }

    static SimpleArg paired(final String name, final int v) {
        return create(ArgSpec.of(name, ""), Optional.of(Value.ofInt(v)));
    }

    static <T> SimpleArg paired(final String name, final T v) {
        return create(ArgSpec.of(name, ""), Optional.of(Value.ofString(v.toString())));
    }
}
