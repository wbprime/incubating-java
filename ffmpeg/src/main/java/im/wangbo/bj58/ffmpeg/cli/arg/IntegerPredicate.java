package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.auto.value.AutoValue;
import com.google.common.primitives.Longs;

import java.util.function.LongPredicate;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
@AutoValue
abstract class IntegerPredicate implements ValueValidator {
    abstract LongPredicate predicate();

    abstract String description();

    static IntegerPredicate of(final LongPredicate predicate, final String description) {
        return new AutoValue_IntegerPredicate(predicate, description);
    }

    @Override
    public boolean validate(final String str) {
        final Long n = Longs.tryParse(str);
        return null != n && predicate().test(n);
    }
}
