package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.ffmpeg.codec.MediaDecoder;
import im.wangbo.bj58.ffmpeg.ffmpeg.seek.Seeking;

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

        public Builder addArg(final Arg arg) {
            args.add(arg);
            return this;
        }

        public Builder decoder(final StreamSpecifier stream, final MediaDecoder decoder) {
            args.add(Arg.paired("-c:" + stream.asString(), decoder.asString()));
            return this;
        }

        public Builder seeking(final Seeking seeking) {
            args.addAll(seeking.asArgs());
            return this;
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
