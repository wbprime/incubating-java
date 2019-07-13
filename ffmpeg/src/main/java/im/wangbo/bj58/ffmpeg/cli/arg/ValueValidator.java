package im.wangbo.bj58.ffmpeg.cli.arg;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
public interface ValueValidator {
    boolean validate(final String str);

    static ValueValidator isTrue() {
        return new AlwaysValid();
    }

    static ValueValidator isFalse() {
        return new AlwaysInvalid();
    }

    static ValueValidator isInteger() {
        return new IntegerPredicate(n -> true);
    }

    static ValueValidator isPositiveInteger() {
        return new IntegerPredicate(n -> n > 0L);
    }

    static ValueValidator isNegativeInteger() {
        return new IntegerPredicate(n -> n < 0L);
    }

    static ValueValidator isNonNegativeInteger() {
        return new IntegerPredicate(n -> n >= 0L);
    }

    static ValueValidator isIntegerRangeIn(final long inclusiveMin, final long exclusiveMax) {
        return new IntegerPredicate(n -> n >= inclusiveMin && n < exclusiveMax);
    }
}
