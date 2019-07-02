package im.wangbo.bj58.ffmpeg.ffmpeg.filter;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.Lists;
import im.wangbo.bj58.ffmpeg.DescribeTo;
import java.util.List;

/**
 * TODO Details go here.
 *
 * Created at 2019-07-01 by Elvis Wang
 */
public class FilterChain implements DescribeTo {

    private final List<Filter> filters;
    private final List<FilterLink> filterLinks;

    public FilterChain(final Filter first) {
        this.filters = Lists.newArrayList(first);
        this.filterLinks = Lists.newArrayList();
    }

    private Filter head() {
        return filters.get(0);
    }

    private Filter tail() {
        return filters.get(filters.size() - 1);
    }

    public FilterChain andThen(final Filter next, final String linkName) {
        final Filter tail = tail();
        checkArgument(tail.spec().outgoings() == next.spec().incomings(),
            "predecessor's outgoings (%s) should equals successor's incoming (%s) but not",
            tail.spec().outgoings(), next.spec().incomings());

        filters.add(next);
        filterLinks.add(FilterLink.of(linkName, next.spec().incomings()));
        return this;
    }

    @Override
    public void describeTo(final StringBuilder sb) {
        FilterLink lastLink = null;
        for (int i = 0; i < filterLinks.size(); i++) {
            final Filter filter = filters.get(i);
            final FilterLink nextLink = filterLinks.get(i);

            if (null != lastLink) {
                sb.append(',');
                lastLink.describeTo(sb);
            }
            filter.describeTo(sb);
            nextLink.describeTo(sb);

            lastLink = nextLink;
        }

        if (null != lastLink) {
            sb.append(',');
            lastLink.describeTo(sb);
        }
        tail().describeTo(sb);
    }
}
