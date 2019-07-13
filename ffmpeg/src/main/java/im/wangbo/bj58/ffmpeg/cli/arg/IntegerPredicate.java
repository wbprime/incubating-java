package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.common.primitives.Longs;

import java.util.function.LongPredicate;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
class IntegerPredicate implements ValueValidator {
    private final LongPredicate predicate;

    public IntegerPredicate(final LongPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean validate(final String str) {
        final Long n = Longs.tryParse(str);
        return null != n && predicate.test(n);
    }
}
