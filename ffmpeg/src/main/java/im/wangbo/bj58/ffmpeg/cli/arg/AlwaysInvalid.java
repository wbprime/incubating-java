package im.wangbo.bj58.ffmpeg.cli.arg;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-13, by Elvis Wang
 */
class AlwaysInvalid implements ValueValidator {
    @Override
    public boolean validate(String str) {
        return false;
    }
}
