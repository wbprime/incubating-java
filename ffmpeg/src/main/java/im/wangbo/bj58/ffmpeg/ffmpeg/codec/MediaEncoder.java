package im.wangbo.bj58.ffmpeg.ffmpeg.codec;

import im.wangbo.bj58.ffmpeg.arg.Value;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaEncoder extends Value {
    String encoderName();

    @Override
    String asString();

    static MediaEncoder named(final String name) {
        return StdEncoder.of(name);
    }
}
