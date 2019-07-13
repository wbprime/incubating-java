package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class RawHevcFormat implements MediaFormat {
    @Override
    public final String name() {
        return "Raw H.265/HEVC video";
    }

    @Override
    public final MediaMuxer muxer() {
        return new RawHevcMuxer();
    }

    @Override
    public final MediaDemuxer demuxer() {
        return new RawHevcDemuxer();
    }

    static RawHevcFormat of() {
        return new AutoValue_RawHevcFormat();
    }

    private static class RawHevcMuxer implements MediaMuxer {
        @Override
        public String muxerName() {
            return "hevc";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }

    private static class RawHevcDemuxer implements MediaDemuxer {
        @Override
        public String demuxerName() {
            return "hevc";
        }

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }
    }
}
