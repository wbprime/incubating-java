package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaCodec {
    MediaEncoder encoder();
    MediaDecoder decoder();

    static MediaCodec of(final String encoder, final String decoder) {
        return StdMediaCodec.create(encoder, decoder);
    }

    static MediaCodec of(final String name) {
        return of(name, name);
    }

    static MediaCodec copying() {
        return of("copy", "copy");
    }
}
