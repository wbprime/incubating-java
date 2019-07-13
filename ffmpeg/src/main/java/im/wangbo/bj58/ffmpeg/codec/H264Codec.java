package im.wangbo.bj58.ffmpeg.codec;

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
abstract class H264Codec implements MediaCodec {
    @Override
    public final String name() {
        return "H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10";
    }

    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(new H264Encoder());
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.of(new H264Decoder());
    }

    static H264Codec of() {
        return new AutoValue_H264Codec();
    }

    private static class H264Encoder implements MediaEncoder {
        @Override
        public String encoderName() {
            return "libx264";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }

    private static class H264Decoder implements MediaDecoder {
        @Override
        public String decoderName() {
            return "h264";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }
}
