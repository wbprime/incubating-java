package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class HlsFormat implements MediaFormat {
    @Override
    public final String name() {
        return "Apple HTTP Live Streaming";
    }

    @Override
    public final MediaMuxer muxer() {
        return StdMuxer.of("hls");
    }

    @Override
    public final MediaDemuxer demuxer() {
        return StdDemuxer.of("hls");
    }

    static HlsFormat of() {
        return new AutoValue_HlsFormat();
    }
}
