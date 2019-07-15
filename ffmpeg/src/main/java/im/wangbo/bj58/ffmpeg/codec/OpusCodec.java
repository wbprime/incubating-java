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
abstract class OpusCodec implements MediaCodec {
    @Override
    public final String name() {
        return "Opus";
    }

    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(new OpusEncoder());
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.of(new OpusDecoder());
    }

    static OpusCodec of() {
        return new AutoValue_OpusCodec();
    }

    private static class OpusEncoder implements MediaEncoder {
        @Override
        public String encoderName() {
            return "libopus";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }

    private static class OpusDecoder implements MediaDecoder {
        @Override
        public String decoderName() {
            return "libopus";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }
}
