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
public interface InputSource {
    List<Arg> asArgs();

    static Builder builder(final String path) {
        return new Builder(path);
    }

    class Builder {
        private final String pathToInput;

        private final List<Arg> args = Lists.newArrayList();

        private Builder(final String path) {
            this.pathToInput = path;
        }

        public InputSource build() {
            final ImmutableList<Arg> inputArgs = ImmutableList.<Arg>builder()
                    .addAll(args)
                    .add(Args.inputUri(pathToInput))
                    .build();
            return () -> inputArgs;
        }
    }
}
