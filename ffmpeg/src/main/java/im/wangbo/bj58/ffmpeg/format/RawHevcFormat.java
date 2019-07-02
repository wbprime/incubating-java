package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class RawHevcFormat implements MediaFormat {
    @Override
    public final String name() {
        return "Raw H.265/HEVC video";
    }

    @Override
    public final MediaMuxer muxer() {
        return StdMuxer.of("hevc");
    }

    @Override
    public final MediaDemuxer demuxer() {
        return StdDemuxer.of("hevc");
    }

    static RawHevcFormat of() {
        return new AutoValue_RawHevcFormat();
    }
}
