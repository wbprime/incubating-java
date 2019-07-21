package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html#gif-1">gif demuxer</a>
 * and <a href="http://ffmpeg.org/ffmpeg-formats.html#gif-2">gif muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class GifFormat implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new GifMuxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new GifDemuxer());
    }

    static GifFormat of() {
        return new AutoValue_GifFormat();
    }

    private static class GifMuxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "gif";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class GifDemuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "gif";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
