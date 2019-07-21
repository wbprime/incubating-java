package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.*;
import im.wangbo.bj58.ffmpeg.codec.MediaCodec;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.net.URI;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface InputSource {
    List<InputArg> asArgs();

    static Builder builder(final URI uri) {
        return new Builder(uri);
    }

    static Builder builder(final Path path) {
        return new Builder(path);
    }

    class Builder {
        private final InputUriArg inputUri;

        private final List<InputArg> args = Lists.newArrayList();

        private List<InputArg> seekingArgs = Collections.emptyList();

        private Builder(final URI uri) {
            this.inputUri = InputUriArg.of(uri);
        }

        private Builder(final Path path) {
            this.inputUri = InputUriArg.of(path);
        }

        public Builder addArg(final InputArg arg) {
            args.add(arg);
            return this;
        }

        public Builder mediaFormat(final MediaFormat f) {
            if (f.demuxer().isPresent())
                addArg(MediaFormatArg.asInput(f.demuxer().get()));
            return this;
        }

        public Builder mediaDecoder(final MediaCodec codec) {
            return mediaDecoder(StreamSpecifier.all(), codec);
        }

        public Builder mediaDecoder(final StreamSpecifier specifier, final MediaCodec codec) {
            if (codec.decoder().isPresent())
                addArg(MediaCodecArg.asInput(specifier, codec.decoder().get()));
            return this;
        }

        /**
         * Seeking from {@code beg} to {@code end}.
         *
         * @param beg beg position offset from beginning
         * @param end end position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingOffsetArg beg, final SeekingEndArg end) {
            seekingArgs = ImmutableList.of(beg, end);
            return this;
        }

        /**
         * Seeking from {@code beg} with {@code duration}.
         *
         * @param beg      beg position offset from beginning
         * @param duration duration
         * @return this
         */
        public Builder seeking(final SeekingOffsetArg beg, final SeekingDurationArg duration) {
            seekingArgs = ImmutableList.of(beg, duration);
            return this;
        }

        /**
         * Seeking from {@code beg} to ending.
         *
         * @param beg beg position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingOffsetArg beg) {
            seekingArgs = ImmutableList.of(beg);
            return this;
        }

        /**
         * Seeking from beginning to {@code end}.
         *
         * @param end end position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingEndArg end) {
            seekingArgs = ImmutableList.of(end);
            return this;
        }

        /**
         * Seeking from begninning with {@code duration}.
         *
         * @param duration duration
         * @return this
         */
        public Builder seeking(final SeekingDurationArg duration) {
            seekingArgs = ImmutableList.of(duration);
            return this;
        }

        /**
         * Seeking from {@code beg} to {@code end}.
         *
         * @param beg beg position offset from ending
         * @param end end position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingBackOffsetArg beg, final SeekingEndArg end) {
            seekingArgs = ImmutableList.of(beg, end);
            return this;
        }

        /**
         * Seeking from {@code beg} with {@code duration}.
         *
         * @param beg      beg position offset from ending
         * @param duration duration
         * @return this
         */
        public Builder seeking(final SeekingBackOffsetArg beg, final SeekingDurationArg duration) {
            seekingArgs = ImmutableList.of(beg, duration);
            return this;
        }

        /**
         * Seeking from {@code beg} to ending.
         *
         * @param beg beg position offset from ending
         * @return this
         */
        public Builder seeking(final SeekingBackOffsetArg beg) {
            seekingArgs = ImmutableList.of(beg);
            return this;
        }

        public InputSource build() {
            final ImmutableList<InputArg> inputArgs = ImmutableList.<InputArg>builder()
                .addAll(args)
                .addAll(seekingArgs)
                .add(inputUri)
                .build();
            return () -> inputArgs;
        }
    }
}
