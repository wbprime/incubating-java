package im.wangbo.bj58.ffmpeg.format;

import java.util.Optional;

/**
 * See outputs of {@code ffmpeg -formats}.
 *
 * @author Elvis Wang
 */
public interface MediaFormat {
    Optional<MediaMuxer> muxer();

    Optional<MediaDemuxer> demuxer();

    static MediaFormat flv() {
        return FlvFormat.of();
    }

    static MediaFormat hls() {
        return FlvFormat.of();
    }

    static MediaFormat image2() {
        return Image2Format.of();
    }
}
