package im.wangbo.bj58.ffmpeg.cli.arg;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
public interface ValueValidator {
    boolean validate(final String str);

    static ValueValidator isTrue() {
        return AlwaysValid.of();
    }

    static ValueValidator isFalse() {
        return AlwaysInvalid.of();
    }

    static ValueValidator isInteger() {
        return IntegerPredicate.of(n -> true, "Always LONG integer");
    }

    static ValueValidator isPositiveInteger() {
        return IntegerPredicate.of(n -> n > 0L, "Always positive LONG integer");
    }

    static ValueValidator isNegativeInteger() {
        return IntegerPredicate.of(n -> n < 0L, "Always negative LONG integer");
    }

    static ValueValidator isNonNegativeInteger() {
        return IntegerPredicate.of(n -> n >= 0L, "Always non-negative LONG integer");
    }

    static ValueValidator isIntegerRangeIn(final long inclusiveMin, final long exclusiveMax) {
        return IntegerPredicate.of(n -> n >= inclusiveMin && n < exclusiveMax,
            String.format("Always as LONG integer range in [%d, %d)", inclusiveMin, exclusiveMax));
    }

    static ValueValidator matches(final String pattern) {
        return MatchesRegex.of(pattern);
    }
}
