package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html#mov_002fmp4_002f3gp_002fQuickTime">mov/mp4 demuxer</a>
 * and <a href="http://ffmpeg.org/ffmpeg-formats.html#mov_002c-mp4_002c-ismv">mov/mp4 muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class Mp4Format implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new Mp4Muxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new Mp4Demuxer());
    }

    static Mp4Format of() {
        return new AutoValue_Mp4Format();
    }

    private static class Mp4Muxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "mp4";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class Mp4Demuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "mp4";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
