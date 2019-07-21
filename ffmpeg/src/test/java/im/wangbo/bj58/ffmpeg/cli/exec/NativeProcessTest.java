package im.wangbo.bj58.ffmpeg.cli.exec;

import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@Disabled("Check InputStream#available")
public class NativeProcessTest {
    @Test
    void test() throws Exception {
        Process p = new ProcessBuilder(
            Lists.immutable.of(
                "echo", "a", "b", "c"
            ).castToList()
        ).start();

        final InputStream stdin = p.getInputStream();

        int n = 10;
        while (n > 0) {
            int available = stdin.available();
            System.out.println(" == " + available);
            if (available > 0) {
                final byte[] buf = new byte[available];
                stdin.read(buf);
                System.out.println(new String(buf));
            }

            Thread.sleep(10000L);

            n--;
        }
    }
}
