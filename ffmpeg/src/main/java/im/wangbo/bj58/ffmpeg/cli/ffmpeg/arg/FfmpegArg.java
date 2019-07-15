package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import im.wangbo.bj58.ffmpeg.cli.ff.arg.FfArg;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FfmpegArg extends FfArg {
    @Override
    String name();

    @Override
    Optional<String> value();
}
