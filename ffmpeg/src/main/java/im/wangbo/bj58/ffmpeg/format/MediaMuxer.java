package im.wangbo.bj58.ffmpeg.format;

import im.wangbo.bj58.ffmpeg.cli.arg.ArgSpec;

import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface MediaMuxer {
    String muxerName();

    List<ArgSpec> args();
}
