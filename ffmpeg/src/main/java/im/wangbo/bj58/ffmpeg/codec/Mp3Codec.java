package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-codecs.html#libmp3lame-1">mp3</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class Mp3Codec implements MediaCodec {
    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(Encoder.create("libmp3lame"));
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.of(Decoder.create("mp3"));
    }

    static Mp3Codec of() {
        return new AutoValue_Mp3Codec();
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
            return new AutoValue_Mp3Codec_Encoder(encoderName);
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
            return new AutoValue_Mp3Codec_Decoder(decoderName);
        }
    }
}
