package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.net.URI;
import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.arg.main.MediaCodecArg;
import im.wangbo.bj58.ffmpeg.arg.main.MediaFormatArg;
import im.wangbo.bj58.ffmpeg.arg.main.OutputUriArg;
import im.wangbo.bj58.ffmpeg.ffmpeg.codec.MediaCodec;
import im.wangbo.bj58.ffmpeg.ffmpeg.format.MediaFormat;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface OutputSink {
    List<OutputArg> asArgs();

    static Builder builder(final String path) {
        return new Builder(path);
    }

    class Builder {
        private final String pathToOutput;

        private final List<OutputArg> args = Lists.newArrayList();

        private Builder(final String outputPath) {
            this.pathToOutput = outputPath;
        }

        public Builder mediaFormat(final MediaFormat f) {
            args.add(MediaFormatArg.asOutput(f));
            return this;
        }

        public Builder mediaEncoder(final MediaCodec codec) {
            return mediaEncoder(StreamSpecifier.all(), codec);
        }

        public Builder mediaEncoder(final StreamSpecifier specifier, final MediaCodec codec) {
            args.add(MediaCodecArg.asOutput(specifier, codec));
            return this;
        }

        public OutputSink build() {
            final ImmutableList<OutputArg> outputArgs = ImmutableList.<OutputArg>builder()
                    .addAll(args)
                    .add(OutputUriArg.of(URI.create(pathToOutput)))
                    .build();
            return () -> outputArgs;
        }
    }
}
