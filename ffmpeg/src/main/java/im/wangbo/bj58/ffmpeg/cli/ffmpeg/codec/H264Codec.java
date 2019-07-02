package im.wangbo.bj58.ffmpeg.cli.ffmpeg.codec;

import com.google.auto.value.AutoValue;

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
    public final MediaEncoder encoder() {
        return StdEncoder.of("libx264");
    }

    @Override
    public final MediaDecoder decoder() {
        return StdDecoder.of("h264");
    }

    static H264Codec of() {
        return new AutoValue_H264Codec();
    }
}
