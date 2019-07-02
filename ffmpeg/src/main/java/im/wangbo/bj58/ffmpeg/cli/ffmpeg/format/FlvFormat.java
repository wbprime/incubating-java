package im.wangbo.bj58.ffmpeg.cli.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class FlvFormat implements MediaFormat {
    @Override
    public final String name() {
        return "FLV (Flash Video)";
    }

    @Override
    public final MediaMuxer muxer() {
        return StdMuxer.of("flv");
    }

    @Override
    public final MediaDemuxer demuxer() {
        return StdDemuxer.of("flv");
    }

    static FlvFormat of() {
        return new AutoValue_FlvFormat();
    }
}
