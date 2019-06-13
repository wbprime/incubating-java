package im.wangbo.bj58.ffmpeg.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdMediaFormat implements MediaFormat {
    abstract String muxerName();

    abstract String demuxerName();

    @Override
    public final MediaMuxer muxer() {
        return StdMuxer.of(muxerName());
    }

    @Override
    public final MediaDemuxer demuxer() {
        return StdDemuxer.of(demuxerName());
    }

    static StdMediaFormat create(final String muxerName, final String demuxerName) {
        return new AutoValue_StdMediaFormat(muxerName, demuxerName);
    }
}
