package im.wangbo.bj58.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.util.Optional;

import im.wangbo.bj58.ffmpeg.arg.GlobalArg;
import im.wangbo.bj58.ffmpeg.ffmpeg.filter.FilterGraph;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class ComplexFilterArg implements GlobalArg {
    @Override
    public final String argName() {
        return "-filter_complex";
    }

    abstract FilterGraph filterGraph();

    @Override
    public final String description() {
        return "Define a complex filtergraph, i.e. one with arbitrary number of inputs and/or outputs." +
                "Input link labels must refer to input streams using the [file_index:stream_specifier] syntax. " +
                "Output link labels are referred to with -map. Unlabeled outputs are added to the first output file." ;
    }

    @Override
    public final Optional<String> argValue() {
        return Optional.of(filterGraph().asString());
    }

    public static ComplexFilterArg of(final FilterGraph graph) {
        return new AutoValue_ComplexFilterArg(graph);
    }
}
