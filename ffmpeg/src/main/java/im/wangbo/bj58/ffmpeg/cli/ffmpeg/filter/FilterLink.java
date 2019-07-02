package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

/*
 * TODO Details go here.
 *
 * Created at 2019-06-24
 * Created by Elvis Wang
 */

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.DescribeTo;

@AutoValue
public abstract class FilterLink implements DescribeTo {
    abstract String prefix();

    abstract int weight();

    @Override
    public void describeTo(StringBuilder sb) {
        for (int i = 0; i < weight(); i++) {
            sb.append('[').append(prefix()).append('_').append(i).append(']');
        }
    }

    public static FilterLink of(final String v, final int weight) {
        return new AutoValue_FilterLink(v, weight);
    }
}
