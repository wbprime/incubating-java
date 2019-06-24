package im.wangbo.bj58.ffmpeg.ffmpeg.filter2;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-06-23, by Elvis Wang
 */
public class SimpleFilterGraph {
    private MutableValueGraph<Node, String> graphBuilder = ValueGraphBuilder.directed().allowsSelfLoops(false).build();
    private Source incoming;
    private Sink outgoing;

    public SimpleFilterGraph add(final Source source, final Filter target) {
        graphBuilder.putEdgeValue(source, target, source.name());
        this.incoming = source;
        return this;
    }

    public SimpleFilterGraph add(final Filter source, final Sink target) {
        graphBuilder.putEdgeValue(source, target, target.name());
        this.outgoing = target;
        return this;
    }

    public SimpleFilterGraph add(final Filter source, final Filter target, final String name) {
        graphBuilder.putEdgeValue(source, target, name);
        return this;
    }
}
