package im.wangbo.bj58.ffmpeg.codec;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    Optional<MediaEncoder> encoder();

    Optional<MediaDecoder> decoder();

    static MediaCodec copying() {
        return CopyingCodec.of();
    }

    static MediaCodec opus() {
        return OpusCodec.of();
    }

    static MediaCodec mp3() {
        return Mp3Codec.of();
    }

    static MediaCodec aac() {
        return AacCodec.of();
    }

    static MediaCodec vorbis() {
        return VorbisCodec.of();
    }

    static MediaCodec flac() {
        return FlacCodec.of();
    }

    static MediaCodec h264() {
        return H264Codec.of();
    }

    static MediaCodec avc() {
        return H264Codec.of();
    }

    static MediaCodec h265() {
        return HevcCodec.of();
    }

    static MediaCodec hevc() {
        return HevcCodec.of();
    }

    static MediaCodec vp8() {
        return Vp8Codec.of();
    }

    static MediaCodec vp9() {
        return Vp9Codec.of();
    }
}
