package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.common.collect.ImmutableList;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
public class ComplexFilterGraph {
    private MutableValueGraph<Node, String> graphBuilder = ValueGraphBuilder.directed().allowsSelfLoops(false).build();
    private ImmutableList<Source> incomings;
    private ImmutableList<Sink> outgoings;

    public ComplexFilterGraph add(final Source source, final Filter target) {
        graphBuilder.putEdgeValue(source, target, source.name());
        return this;
    }

    public ComplexFilterGraph add(final Filter source, final Sink target) {
        graphBuilder.putEdgeValue(source, target, target.name());
        return this;
    }

    public ComplexFilterGraph add(final Filter source, final Filter target, final String name) {
        graphBuilder.putEdgeValue(source, target, name);
        return this;
    }
}
