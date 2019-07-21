package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html">ffmpeg muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class WebpFormat implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(Muxer.create("webp"));
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(Demuxer.create("webp_pipe"));
    }

    static WebpFormat of() {
        return new AutoValue_WebpFormat();
    }

    @AutoValue
    static abstract class Muxer implements MediaMuxer {
        @Override
        public abstract String muxerName();

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }

        static Muxer create(String muxerName) {
            return new AutoValue_WebpFormat_Muxer(muxerName);
        }
    }

    @AutoValue
    static abstract class Demuxer implements MediaDemuxer {
        @Override
        public abstract String demuxerName();

        @Override
        public List<ArgSpec> args() {
            // TODO
            return Collections.emptyList();
        }

        static Demuxer create(String demuxerName) {
            return new AutoValue_WebpFormat_Demuxer(demuxerName);
        }
    }
}
