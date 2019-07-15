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
abstract class FlvFormat implements MediaFormat {
    @Override
    public final String name() {
        return "FLV (Flash Video)";
    }

    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new FlvMuxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new FlvDemuxer());
    }

    static FlvFormat of() {
        return new AutoValue_FlvFormat();
    }

    private static class FlvMuxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "flv";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class FlvDemuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "flv";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
