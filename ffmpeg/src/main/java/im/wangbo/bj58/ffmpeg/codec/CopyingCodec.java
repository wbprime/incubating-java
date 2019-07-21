package im.wangbo.bj58.ffmpeg.codec;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * See <a href="http://ffmpeg.org/ffmpeg.html#Stream-copy">Stream Copy</a> for details.
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class CopyingCodec implements MediaCodec {
    @Override
    public final Optional<MediaEncoder> encoder() {
        return Optional.of(Encoder.create("copy"));
    }

    @Override
    public final Optional<MediaDecoder> decoder() {
        return Optional.empty();
    }

    static CopyingCodec of() {
        return new AutoValue_CopyingCodec();
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
            return new AutoValue_CopyingCodec_Encoder(encoderName);
        }
    }
}
