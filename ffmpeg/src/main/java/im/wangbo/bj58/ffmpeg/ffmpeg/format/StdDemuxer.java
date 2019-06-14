package im.wangbo.bj58.ffmpeg.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdDemuxer implements MediaDemuxer {
    @Override
    public abstract String demuxerName();

    static StdDemuxer of(final String name) {
        return new AutoValue_StdDemuxer(name);
    }
}
