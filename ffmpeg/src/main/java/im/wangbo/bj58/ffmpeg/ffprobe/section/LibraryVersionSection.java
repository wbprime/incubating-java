package im.wangbo.bj58.ffmpeg.ffprobe.section;

import java.util.StringJoiner;

/**
 * Sample data:
 *
 * "library_version": {
 *    "name": "libavutil",
 *    "major": 56,
 *    "minor": 22,
 *    "micro": 100,
 *    "version": 3675748,
 *    "ident": "Lavu56.22.100"
 *  }
 *
 * @author Elvis Wang
 */
public class LibraryVersionSection {
    @Override
    public String toString() {
        return new StringJoiner(", ", LibraryVersionSection.class.getSimpleName() + "[", "]")
                .toString();
    }
}
