package im.wangbo.bj58.ffmpeg.common;

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
public abstract class Pair implements Arg {
    public abstract Name name();
    public abstract Value value();

    @Override
    public final List<String> encode() {
        return ImmutableList.of(name().name(), value().value());
    }

    public static Pair of(final Name name, final Value value) {
        return new AutoValue_Pair(name, value);
    }
}
