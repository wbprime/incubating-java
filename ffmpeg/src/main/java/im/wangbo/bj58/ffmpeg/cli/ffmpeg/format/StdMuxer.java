package im.wangbo.bj58.ffmpeg.cli.ffmpeg.format;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
abstract class StdMuxer implements MediaMuxer {
    @Override
    public abstract String muxerName();

    static StdMuxer of(final String name) {
        return new AutoValue_StdMuxer(name);
    }
}

