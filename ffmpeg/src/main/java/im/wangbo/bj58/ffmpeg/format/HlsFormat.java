package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class HlsFormat implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new HlsMuxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new HlsDemuxer());
    }

    static HlsFormat of() {
        return new AutoValue_HlsFormat();
    }

    private static class HlsMuxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "hls";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class HlsDemuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "hls";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
