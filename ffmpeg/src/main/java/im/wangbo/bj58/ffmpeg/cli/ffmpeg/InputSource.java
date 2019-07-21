package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.FrameRateArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InputArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InputUriArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.MediaCodecArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.MediaFormatArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.SeekingBackOffsetArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.SeekingDurationArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.SeekingEndArg;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.SeekingOffsetArg;
import im.wangbo.bj58.ffmpeg.codec.MediaCodec;
import im.wangbo.bj58.ffmpeg.common.FrameRate;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.factory.Lists;

import java.net.URI;
import java.nio.file.Path;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface InputSource {
    ImmutableList<InputArg> asArgs();

    static Builder builder(final URI uri) {
        return new Builder(uri);
    }

    static Builder builder(final Path path) {
        return new Builder(path);
    }

    class Builder {
        private final InputUriArg inputUri;

        private final MutableList<InputArg> args = Lists.mutable.empty();

        private ImmutableList<InputArg> seekingArgs = Lists.immutable.empty();

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

        /**
         * "-f" option
         *
         * @param format muxer
         * @return this
         */
        public Builder mediaFormat(final MediaFormat format) {
            if (format.demuxer().isPresent())
                addArg(MediaFormatArg.asInput(format.demuxer().get()));
            return this;
        }

        /**
         * "-c" option
         *
         * @param codec encoder
         * @return this
         */
        public Builder mediaDecoder(final MediaCodec codec) {
            return mediaDecoder(StreamSpecifier.all(), codec);
        }

        /**
         * "-c:v" - like option
         *
         * @param specifier stream specifier
         * @param codec     encoder
         * @return this
         */
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
            seekingArgs = Lists.immutable.of(beg, end);
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
            seekingArgs = Lists.immutable.of(beg, duration);
            return this;
        }

        /**
         * Seeking from {@code beg} to ending.
         *
         * @param beg beg position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingOffsetArg beg) {
            seekingArgs = Lists.immutable.of(beg);
            return this;
        }

        /**
         * Seeking from beginning to {@code end}.
         *
         * @param end end position offset from beginning
         * @return this
         */
        public Builder seeking(final SeekingEndArg end) {
            seekingArgs = Lists.immutable.of(end);
            return this;
        }

        /**
         * Seeking from begninning with {@code duration}.
         *
         * @param duration duration
         * @return this
         */
        public Builder seeking(final SeekingDurationArg duration) {
            seekingArgs = Lists.immutable.of(duration);
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
            seekingArgs = Lists.immutable.of(beg, end);
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
            seekingArgs = Lists.immutable.of(beg, duration);
            return this;
        }

        /**
         * Seeking from {@code beg} to ending.
         *
         * @param beg beg position offset from ending
         * @return this
         */
        public Builder seeking(final SeekingBackOffsetArg beg) {
            seekingArgs = Lists.immutable.of(beg);
            return this;
        }

        /**
         * "-r" option
         *
         * @param fps frame rate
         * @return this
         */
        public Builder frameRate(final FrameRate fps) {
            return frameRate(StreamSpecifier.all(), fps);
        }

        /**
         * "-r:v" - like option
         *
         * @param specifier stream specifier
         * @param fps       frame rate
         * @return this
         */
        public Builder frameRate(final StreamSpecifier specifier, final FrameRate fps) {
            return addArg(FrameRateArg.asInput(specifier, fps));
        }

        /**
         * @return a new {@link InputSource} instance
         */
        public InputSource build() {
            final MutableList<InputArg> inputArgs = Lists.mutable.<InputArg>empty()
                .withAll(args)
                .withAll(seekingArgs)
                .with(inputUri);
            return inputArgs::toImmutable;
        }
    }
}
