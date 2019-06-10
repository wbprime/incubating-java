package im.wangbo.bj58.ffmpeg.filter;

import com.google.common.collect.ImmutableList;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Filter extends FilterChain {
    String typeId();

    ImmutableList<FilterPad> input();

    ImmutableList<FilterPad> output();

    List<Arg> args();

    @Override
    default ImmutableList<Filter> filters() {
        return ImmutableList.of(this);
    }

    interface FilterBuilder {
        Filter build();
    }
}
