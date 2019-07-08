package im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
public class SimpleFilterGraph {

    private final MutableValueGraph<FilterChain, FilterLink> graph = ValueGraphBuilder.directed()
        .allowsSelfLoops(false)
        .nodeOrder(ElementOrder.insertion())
        .build();

    private SimpleFilterGraph() {
    }

    public SimpleFilterGraph add(
        final FilterChain predecessor, final FilterChain successor, final FilterLink link
    ) {
        graph.putEdgeValue(predecessor, successor, link);
        return this;
    }

    public void describeTo(final StringBuilder sb) {
        graph.nodes().forEach(chain -> chain.describeTo(sb));
    }

    public static SimpleFilterGraph create() {
        return new SimpleFilterGraph();
    }
}
