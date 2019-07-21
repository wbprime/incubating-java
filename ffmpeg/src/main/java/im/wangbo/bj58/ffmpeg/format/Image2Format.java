package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html#image2-1">image2 demuxer</a>
 * and <a href="http://ffmpeg.org/ffmpeg-formats.html#image2-2">image2 muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class Image2Format implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new Image2Muxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new Image2Demuxer());
    }

    static Image2Format of() {
        return new AutoValue_Image2Format();
    }

    private static class Image2Muxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "image2";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class Image2Demuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "image2";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
