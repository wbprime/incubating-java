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

    static MediaFormat image2() {
        return Image2Format.of();
    }

    static MediaFormat gif() {
        return GifFormat.of();
    }

    static MediaFormat flv() {
        return FlvFormat.of();
    }

    static MediaFormat mp4() {
        return Mp4Format.of();
    }

    static MediaFormat avi() {
        return AviFormat.of();
    }

    static MediaFormat hls() {
        return HlsFormat.of();
    }
}
