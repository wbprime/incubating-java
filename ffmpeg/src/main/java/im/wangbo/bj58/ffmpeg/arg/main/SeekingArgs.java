package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.common.collect.ImmutableList;

import im.wangbo.bj58.ffmpeg.arg.InOutputArg;
import im.wangbo.bj58.ffmpeg.arg.InputArg;
import im.wangbo.bj58.ffmpeg.common.SeekDuration;
import im.wangbo.bj58.ffmpeg.common.SeekPosition;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class SeekingArgs {
    private SeekingArgs() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    /**
     * Determine a seeking range from {@code beg} with duration {@code d}.
     *
     * @param beg range beg (offset from file beginning, i.e., 0)
     * @param d range duration
     * @return seeking range args
     */
    public static ImmutableList<? super InOutputArg> range(final SeekPosition beg, final SeekDuration d) {
        return ImmutableList.of(ForwardStartPositionArg.of(beg), DurationArg.of(d));
    }

    /**
     * Determine a seeking range from {@code beg} to {@code end}.
     *
     * @param beg range beg (offset from file beginning, i.e., 0)
     * @param end range end (offset from file beginning, i.e., 0)
     * @return seeking range args
     */
    public static ImmutableList<? super InOutputArg> range(final SeekPosition beg, final SeekPosition end) {
        return ImmutableList.of(ForwardStartPositionArg.of(beg), EndPositionArg.of(end));
    }

    /**
     * Determine a seeking range from file beginning with duration {@code d}.
     *
     * @param d range duration
     * @return seeking range args
     */
    public static ImmutableList<? super InOutputArg> range(final SeekDuration d) {
        return ImmutableList.of(DurationArg.of(d));
    }

    /**
     * Determine a seeking range from {@code beg} to file ending.
     *
     * @param beg range beg (offset from file beginning, i.e., 0)
     * @return seeking range args
     */
    public static ImmutableList<? super InOutputArg> rangeFrom(final SeekPosition beg) {
        return ImmutableList.of(ForwardStartPositionArg.of(beg));
    }

    /**
     * Determine a seeking range from file beginning to {@code end}.
     *
     * @param end range end (offset from file beginning, i.e., 0)
     * @return seeking range args
     */
    public static ImmutableList<? super InOutputArg> rangeTo(final SeekPosition end) {
        return ImmutableList.of(EndPositionArg.of(end));
    }

    /**
     * Determine a seeking range from {@code backwardBeg} with duration {@code d}.
     *
     * @param backwardBeg range beg (offset from file ending, i.e., eof)
     * @param d range duration
     * @return seeking range args
     */
    public static ImmutableList<InputArg> backwardRange(final SeekPosition backwardBeg, final SeekDuration d) {
        return ImmutableList.of(BackwardStartPositionArg.of(backwardBeg), DurationArg.of(d));
    }

    /**
     * Determine a seeking range from {@code backwardBeg} to {@code end}.
     *
     * @param backwardBeg range beg (offset from file ending, i.e., eof)
     * @param end range end (offset from file beginning, i.e., 0)
     * @return seeking range args
     */
    public static ImmutableList<InputArg> backwardRange(final SeekPosition backwardBeg, final SeekPosition end) {
        return ImmutableList.of(BackwardStartPositionArg.of(backwardBeg), EndPositionArg.of(end));
    }

    /**
     * Determine a seeking range from {@code backwardBeg} to file ending.
     *
     * @param backwardBeg range beg (offset from file ending, i.e., eof)
     * @return seeking range args
     */
    public static ImmutableList<InputArg> backwardRangeFrom(final SeekPosition backwardBeg) {
        return ImmutableList.of(BackwardStartPositionArg.of(backwardBeg));
    }
}
