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
abstract class HlsFormat implements MediaFormat {
    @Override
    public final String name() {
        return "Apple HTTP Live Streaming";
    }

    @Override
    public final MediaMuxer muxer() {
        return new HlsMuxer();
    }

    @Override
    public final MediaDemuxer demuxer() {
        return new HlsDemuxer();
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
