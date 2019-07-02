package im.wangbo.bj58.ffmpeg.cli.ffmpeg.codec;

import com.google.auto.value.AutoValue;

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
        return StdEncoder.of("libx265");
    }

    @Override
    public final MediaDecoder decoder() {
        return StdDecoder.of("hevc");
    }

    static HevcCodec of() {
        return new AutoValue_HevcCodec();
    }
}
