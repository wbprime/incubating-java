package im.wangbo.bj58.ffmpeg.ffmpeg;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface InputSource {
    List<Arg> asArgs();
}
