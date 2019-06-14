package im.wangbo.bj58.ffmpeg.ffmpeg.format;

import javax.annotation.Nullable;

/**
 * See outputs of {@code ffmpeg -formats}.
 *
 * @author Elvis Wang
 */
public interface MediaFormat {
    String name();

    /**
     * @return muxer if muxing supported, or null if not supported
     */
    @Nullable
    MediaMuxer muxer();

    /**
     * @return demuxer if demuxing supported, or null if not supported
     */
    @Nullable
    MediaDemuxer demuxer();
}
