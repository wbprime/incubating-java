package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.OutputArg;
import im.wangbo.bj58.ffmpeg.streamspecifier.StreamSpecifier;
import im.wangbo.bj58.ffmpeg.cli.ffmpeg.filter.SimpleFilterGraph;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class SimpleFilterArg implements OutputArg {

    @Override
    public final String name() {
        final String specifier = streamSpecifier().asString();
        return specifier.isEmpty() ? "-filter" : "-filter:" + specifier;
    }

    abstract StreamSpecifier streamSpecifier();

    abstract SimpleFilterGraph filterGraph();

    @Override
    public final String description() {
        return "Create the filtergraph specified by filtergraph and use it to filter the stream. " +
            "filtergraph is a description of the filtergraph to apply to the stream," +
            " and must have a single input and a single output of the same type of the stream." +
            " In the filtergraph, the input is associated to the label in, and the output to the label out."
            +
            " See the ffmpeg-filters manual for more information about the filtergraph syntax.";
    }

    @Override
    public final Optional<String> value() {
        return Optional.of(filterGraph().toString());
    }

    public static SimpleFilterArg of(final StreamSpecifier streamSpecifier,
        final SimpleFilterGraph graph) {
        return new AutoValue_SimpleFilterArg(streamSpecifier, graph);
    }
}
