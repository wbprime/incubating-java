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
abstract class CopyingCodec implements MediaCodec {
    @Override
    public final String name() {
        return "Copy as is";
    }

    @Override
    public final MediaEncoder encoder() {
        return new CopyingEncoder();
    }

    @Override
    public final MediaDecoder decoder() {
        return new CopyingDecoder();
    }

    static CopyingCodec of() {
        return new AutoValue_CopyingCodec();
    }

    private static class CopyingEncoder implements MediaEncoder {
        @Override
        public String encoderName() {
            return "copy";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }

    private static class CopyingDecoder implements MediaDecoder {
        @Override
        public String decoderName() {
            return "copy";
        }

        @Override
        public List<ArgSpec> args() {
            return Collections.emptyList();
        }
    }
}
