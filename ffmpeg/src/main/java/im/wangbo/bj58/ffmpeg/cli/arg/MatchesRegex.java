package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.auto.value.AutoValue;

import java.util.regex.Pattern;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
@AutoValue
abstract class MatchesRegex implements ValueValidator {
    abstract Pattern pattern();

    static MatchesRegex of(final Pattern pattern) {
        return new AutoValue_MatchesRegex(pattern);
    }

    static MatchesRegex of(final String pattern) {
        return of(Pattern.compile(pattern));
    }

    @Override
    public boolean validate(final String str) {
        return pattern().matcher(str).matches();
    }
}
