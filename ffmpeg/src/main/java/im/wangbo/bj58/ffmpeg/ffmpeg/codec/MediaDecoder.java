package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaDecoder extends Value {
    String decoderName();

    @Override
    String asString();

    static MediaDecoder named(final String name) {
        return StdDecoder.of(name);
    }
}
