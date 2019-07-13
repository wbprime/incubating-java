package im.wangbo.bj58.ffmpeg.cli.arg;

import java.util.regex.Pattern;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
class MatchesRegex implements ValueValidator {
    private final Pattern pattern;

    public MatchesRegex(final String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean validate(String str) {
        return pattern.matcher(str).matches();
    }
}
