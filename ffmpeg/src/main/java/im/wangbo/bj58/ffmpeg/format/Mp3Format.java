package im.wangbo.bj58.ffmpeg.format;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-formats.html#mp3">mp3 muxer</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class Mp3Format implements MediaFormat {
    @Override
    public final Optional<MediaMuxer> muxer() {
        return Optional.of(Muxer.create("mp3"));
    }

    @Override
    public final Optional<MediaDemuxer> demuxer() {
        return Optional.of(Demuxer.create("mp3"));
    }

    static Mp3Format of() {
        return new AutoValue_Mp3Format();
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
            return new AutoValue_Mp3Format_Muxer(muxerName);
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
            return new AutoValue_Mp3Format_Demuxer(demuxerName);
        }
    }
}
