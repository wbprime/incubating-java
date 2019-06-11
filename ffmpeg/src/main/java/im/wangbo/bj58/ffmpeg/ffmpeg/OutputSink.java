package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface OutputSink {
    List<Arg> asArgs();

    static Builder builder(final String path) {
        return new Builder(path);
    }

    class Builder {
        private final String pathToOutput;

        private final List<Arg> args = Lists.newArrayList();

        private Builder(final String outputPath) {
            this.pathToOutput = outputPath;
        }

        public OutputSink build() {
            final ImmutableList<Arg> outputArgs = ImmutableList.<Arg>builder()
                    .addAll(args)
                    .add(Args.outputUri(pathToOutput))
                    .build();
            return () -> outputArgs;
        }
    }
}
