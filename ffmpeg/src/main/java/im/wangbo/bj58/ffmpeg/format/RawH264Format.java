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
abstract class RawH264Format implements MediaFormat {
    @Override
    public final String name() {
        return "Raw H.264/AVC video";
    }

    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(new RawH264Muxer());
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(new RawH264Demuxer());
    }

    static RawH264Format of() {
        return new AutoValue_RawH264Format();
    }

    private static class RawH264Muxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "h264";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class RawH264Demuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "h264";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
