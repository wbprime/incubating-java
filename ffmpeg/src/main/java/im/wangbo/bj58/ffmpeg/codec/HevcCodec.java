package im.wangbo.bj58.ffmpeg.codec;

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
abstract class HevcCodec implements MediaCodec {
    @Override
    public final String name() {
        return "H.265 / HEVC";
    }

    @Override
    public final MediaEncoder encoder() {
        return new HevcEncoder();
    }

    @Override
    public final MediaDecoder decoder() {
        return new HevcDecoder();
    }

    static HevcCodec of() {
        return new AutoValue_HevcCodec();
    }

    private static class HevcEncoder implements MediaEncoder {
        @Override
        public String encoderName() {
            return "libx265";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }

    private static class HevcDecoder implements MediaDecoder {
        @Override
        public String decoderName() {
            return "hevc";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }
}
