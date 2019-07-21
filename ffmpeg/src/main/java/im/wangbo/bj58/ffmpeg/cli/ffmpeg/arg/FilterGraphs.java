package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.Filter;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterChain;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.FilterGraph;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-07-21, by Elvis Wang
 */
final class FilterGraphs {
    static String describe(final FilterGraph graph) {
        final StringBuilder sb = new StringBuilder();
        describeTo(graph, sb);
        return sb.toString();
    }

    static void describeTo(final FilterGraph graph, final StringBuilder sb) {
        graph.chains()
            .reject(chain -> chain.filters().isEmpty())
            .each(
                chain -> {
                    describeTo(chain, sb);
                    sb.append(';');
                }
            );

        // Remove trailing ';' character
        int n = sb.length();
        while (n > 0 && sb.charAt(n - 1) == ';') {
            n --;
        }
        sb.setLength(n);
    }

    static String describe(final FilterChain chain) {
        final StringBuilder sb = new StringBuilder();
        describeTo(chain, sb);
        return sb.toString();
    }

    static void describeTo(final FilterChain chain, final StringBuilder sb) {
        if (!chain.filters().isEmpty()) {
            chain.filters().each(
                filter -> {
                    describeTo(filter, sb);
                    sb.append(',');
                }
            );

            sb.setLength(sb.length() - 1);
        }
    }

    static String describe(final Filter filter) {
        final StringBuilder sb = new StringBuilder();
        describeTo(filter, sb);
        return sb.toString();
    }

    static void describeTo(final Filter filter, final StringBuilder sb) {
        filter.incomings().each(str -> sb.append('[').append(str).append(']'));

        sb.append(filter.type());

        if (!filter.args().isEmpty()) {
            sb.append('=');

            filter.args().each(arg -> {
                sb.append(arg.name());
                arg.value().ifPresent(v -> sb.append('=').append(v));
                sb.append(':');
            });

            sb.setLength(sb.length() - 1);
        }

        filter.outgoings().each(str -> sb.append('[').append(str).append(']'));
    }
}
