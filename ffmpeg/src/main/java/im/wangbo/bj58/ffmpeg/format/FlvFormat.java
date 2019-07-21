package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html#flv_002c-live_005fflv">flv demuxer</a>
 * and <a href="http://ffmpeg.org/ffmpeg-formats.html#flv">flv muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class FlvFormat implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(Muxer.create("flv"));
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(Demuxer.create("flv"));
    }

    static FlvFormat of() {
        return new AutoValue_FlvFormat();
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

        static Muxer create(final String name) {
            return new AutoValue_FlvFormat_Muxer(name);
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

        static Demuxer create(final String name) {
            return new AutoValue_FlvFormat_Demuxer(name);
        }
    }
}
