package im.wangbo.bj58.ffmpeg.cli.exec;

import java.util.function.Consumer;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-07, by Elvis Wang
 */
public class StdoutCollector implements Consumer<String> {
    private final StringBuilder sb = new StringBuilder();

    public static StdoutCollector of() {
        return new StdoutCollector();
    }

    @Override
    public void accept(final String s) {
        if (sb.length() == 0) {
            sb.append(s);
        } else {
            sb.append(System.lineSeparator()).append(s);
        }
    }

    public String collect() {
        return sb.toString();
    }

}
