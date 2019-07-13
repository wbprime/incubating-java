package im.wangbo.bj58.ffmpeg.format;

import java.util.Optional;

/**
 * See outputs of {@code ffmpeg -formats}.
 *
 * @author Elvis Wang
 */
public interface MediaFormat {
    String name();

    Optional<MediaMuxer> muxer();

    Optional<MediaDemuxer> demuxer();
}
