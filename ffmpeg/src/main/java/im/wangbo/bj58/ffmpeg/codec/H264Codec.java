package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg-codecs.html#libx264_002c-libx264rgb">libx264</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class H264Codec implements MediaCodec {
    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(Encoder.create("libx264"));
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.of(Decoder.create("h264"));
    }

    static H264Codec of() {
        return new AutoValue_H264Codec();
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
            return new AutoValue_H264Codec_Encoder(encoderName);
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
            return new AutoValue_H264Codec_Decoder(decoderName);
        }
    }
}
