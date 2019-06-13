package im.wangbo.bj58.ffmpeg.ffmpeg.format;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaFormat {
    MediaMuxer muxer();
    MediaDemuxer demuxer();
}
