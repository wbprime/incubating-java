package im.wangbo.bj58.ffmpeg.cli.ffprobe.section;

import java.util.StringJoiner;

/**
 * Sample data:
 * <p>
 * "packet": {
 * "codec_type": "video",
 * "stream_index": 0,
 * "pts": 0,
 * "pts_time": "0.000000 s",
 * "dts": 0,
 * "dts_time": "0.000000 s",
 * "duration": 512,
 * "duration_time": "0.040000 s",
 * "size": "146067 byte",
 * "pos": "184002",
 * "flags": "K_"
 * }
 *
 * @author Elvis Wang
 */
public class PacketSection {
    @Override
    public String toString() {
        return new StringJoiner(", ", PacketSection.class.getSimpleName() + "[", "]")
            .toString();
    }
}
