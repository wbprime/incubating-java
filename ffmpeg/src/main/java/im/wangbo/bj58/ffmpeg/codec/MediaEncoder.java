package im.wangbo.bj58.ffmpeg.codec;

import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaEncoder {
    String encoderName();

    List<ArgSpec> args();
}
