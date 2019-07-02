package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

import java.util.StringJoiner;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class ProgramSection {
    @Override
    public String toString() {
        return new StringJoiner(", ", ProgramSection.class.getSimpleName() + "[", "]")
                .toString();
    }
}
