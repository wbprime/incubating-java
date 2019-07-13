package im.wangbo.bj58.ffmpeg.cli.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.*;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.SimpleFilterGraph;
import im.wangbo.bj58.ffmpeg.codec.MediaCodec;
import im.wangbo.bj58.ffmpeg.common.SizeInByte;
import im.wangbo.bj58.ffmpeg.format.MediaFormat;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;

import java.net.URI;
import java.util.Collections;
import java.util.List;

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

        private List<OutputArg> seekingArgs = Collections.emptyList();

        private Builder(final String outputPath) {
            this.pathToOutput = outputPath;
        }

        public Builder addArg(final OutputArg arg) {
            args.add(arg);
            return this;
        }

        public Builder mediaFormat(final MediaFormat f) {
            if (f.muxer().isPresent())
                addArg(MediaFormatArg.asOutput(f.muxer().get()));
            return this;
        }

        public Builder mediaEncoder(final MediaCodec codec) {
            return mediaEncoder(StreamSpecifier.all(), codec);
        }

        public Builder mediaEncoder(final StreamSpecifier specifier, final MediaCodec codec) {
            if (codec.encoder().isPresent())
                addArg(MediaCodecArg.asOutput(specifier, codec.encoder().get()));
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

        public Builder limitOutputSize(final SizeInByte size) {
            return addArg(OutputFileSizeLimitArg.of(size));
        }

        public Builder limitOutputFrames(final int n) {
            return limitOutputFrames(StreamSpecifier.all(), n);
        }

        public Builder limitOutputFrames(final StreamSpecifier specifier, final int n) {
            return addArg(OutputFramesLimitArg.of(specifier, n));
        }

        public Builder limitOutputQuality(final int n) {
            return addArg(OutputQualityLimitArg.of(StreamSpecifier.all(), n));
        }

        public Builder limitOutputQuality(final StreamSpecifier specifier, final int n) {
            return addArg(OutputQualityLimitArg.of(specifier, n));
        }

        public Builder addMetadata(final String key, final String val) {
            return addArg(MetadataArg.of(key, val));
        }

        public Builder filter(final SimpleFilterGraph graph) {
            return addArg(SimpleFilterArg.of(StreamSpecifier.all(), graph));
        }

        public Builder filter(final StreamSpecifier specifier, final SimpleFilterGraph graph) {
            return addArg(SimpleFilterArg.of(specifier, graph));
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
