package im.wangbo.bj58.ffmpeg.cli.arg;

import com.google.common.primitives.Longs;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
public class IsNegativeInteger implements ValueValidator {
    @Override
    public boolean validate(String str) {
        final Long n = Longs.tryParse(str);
        return null != n && n < 0;
    }
}
