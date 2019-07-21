package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-codecs.html#libvpx">libvpx</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class Vp8Codec implements MediaCodec {
    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(Encoder.create("libvpx"));
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.of(Decoder.create("libvpx"));
    }

    static Vp8Codec of() {
        return new AutoValue_Vp8Codec();
    }

    @AutoValue
    static abstract class Encoder implements MediaEncoder {
        @Override
        public abstract String encoderName();

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }

        static Encoder create(String encoderName) {
            return new AutoValue_Vp8Codec_Encoder(encoderName);
        }
    }

    @AutoValue
    static abstract class Decoder implements MediaDecoder {
        @Override
        public abstract String decoderName();

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }

        static Decoder create(String decoderName) {
            return new AutoValue_Vp8Codec_Decoder(decoderName);
        }
    }
}
