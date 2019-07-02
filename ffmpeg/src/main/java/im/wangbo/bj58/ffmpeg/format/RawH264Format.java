package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class RawH264Format implements MediaFormat {
    @Override
    public final String name() {
        return "Raw H.264/AVC video";
    }

    @Override
    public final MediaMuxer muxer() {
        return StdMuxer.of("h264");
    }

    @Override
    public final MediaDemuxer demuxer() {
        return StdDemuxer.of("h264");
    }

    static RawH264Format of() {
        return new AutoValue_RawH264Format();
    }
}
