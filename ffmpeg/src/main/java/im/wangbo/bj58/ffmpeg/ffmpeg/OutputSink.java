package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.net.URI;
import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.arg.main.OutputUriArg;

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

        public OutputSink build() {
            final ImmutableList<OutputArg> outputArgs = ImmutableList.<OutputArg>builder()
                    .addAll(args)
                    .add(OutputUriArg.of(URI.create(pathToOutput)))
                    .build();
            return () -> outputArgs;
        }
    }
}
