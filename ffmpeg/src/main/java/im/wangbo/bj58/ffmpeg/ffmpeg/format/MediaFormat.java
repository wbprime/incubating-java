package im.wangbo.bj58.ffmpeg.ffmpeg.format;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaFormat {
    MediaMuxer muxer();

    MediaDemuxer demuxer();

    static MediaFormat of(final String muxerName, final String demuxerName) {
        return StdMediaFormat.create(muxerName, demuxerName);
    }

    static MediaFormat of(final String name) {
        return of(name, name);
    }
}
