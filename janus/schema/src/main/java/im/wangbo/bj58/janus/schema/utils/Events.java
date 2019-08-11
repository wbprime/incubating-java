package im.wangbo.bj58.janus.schema.utils;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
public final class Events {
    private Events() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static String address(final Class<?> clz) {
        return "e/" + clz.getName();
    }

    public static String codecName(final Class<?> clz) {
        return "c/" + clz.getName();
    }
}
