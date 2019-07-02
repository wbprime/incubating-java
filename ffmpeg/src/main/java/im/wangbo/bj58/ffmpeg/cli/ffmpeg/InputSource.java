package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.net.URI;
import java.util.List;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main.InputUriArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main.MediaCodecArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main.MediaFormatArg;
import im.wangbo.bj58.ffmpeg.codec.MediaCodec;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface InputSource {
    List<InputArg> asArgs();

    static Builder builder(final String path) {
        return new Builder(path);
    }

    class Builder {
        private final String pathToInput;

        private final List<InputArg> args = Lists.newArrayList();

        private Builder(final String path) {
            this.pathToInput = path;
        }

        public Builder addArg(final InputArg arg) {
            args.add(arg);
            return this;
        }

        public Builder mediaFormat(final MediaFormat f) {
            return addArg(MediaFormatArg.asInput(f));
        }

        public Builder mediaDecoder(final MediaCodec codec) {
            return mediaDecoder(StreamSpecifier.all(), codec);
        }

        public Builder mediaDecoder(final StreamSpecifier specifier, final MediaCodec codec) {
            return addArg(MediaCodecArg.asInput(specifier, codec));
        }

        public Builder seeking(final ImmutableList<InputArg> seeking) {
            seeking.forEach(this::addArg);
            return this;
        }

        public InputSource build() {
            final ImmutableList<InputArg> inputArgs = ImmutableList.<InputArg>builder()
                    .addAll(args)
                    .add(InputUriArg.of(URI.create(pathToInput)))
                    .build();
            return () -> inputArgs;
        }
    }
}
