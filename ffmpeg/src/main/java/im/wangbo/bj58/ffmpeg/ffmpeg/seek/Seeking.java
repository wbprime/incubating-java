package im.wangbo.bj58.ffmpeg.ffmpeg.seek;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import javax.annotation.Nullable;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.SeekDuration;
import im.wangbo.bj58.ffmpeg.arg.SeekPosition;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class Seeking {
    public abstract ImmutableList<Arg> asArgs();

    public static Seeking of(ImmutableList<Arg> asArgs) {
        return new AutoValue_Seeking(asArgs);
    }

    public static final class Builder {
        @Nullable
        private SeekPosition start;

        private boolean backward = false;

        @Nullable
        private SeekPosition end;
        @Nullable
        private SeekDuration duration;

        public Builder from(final SeekPosition position, final boolean backward) {
            this.start = position;
            this.backward = backward;
            return this;
        }

        public Builder from(final SeekPosition position) {
            return from(position, false);
        }

        public Builder to(final SeekPosition position) {
            this.end = position;
            this.duration = null;
            return this;
        }

        public Builder duration(final SeekDuration duration) {
            this.end = null;
            this.duration = duration;
            return this;
        }

        public Seeking build() {
            final ImmutableList.Builder<Arg> builder = ImmutableList.builder();

            if (null != start) {
                builder.add(backward ? BackwardStartPositionArg.of(start) : ForwardStartPositionArg.of(start));
            }

            if (null != end) {
                builder.add(EndPositionArg.of(end));
            }

            if (null != duration) {
                builder.add(DurationArg.of(duration));
            }

            return Seeking.of(builder.build());
        }
    }
}
