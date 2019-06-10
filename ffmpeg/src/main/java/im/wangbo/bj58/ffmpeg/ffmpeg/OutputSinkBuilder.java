package im.wangbo.bj58.ffmpeg.ffmpeg;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;

import im.wangbo.bj58.ffmpeg.arg.Arg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public class OutputSinkBuilder {
    private String pathToOutput;

    private List<Arg> args = Lists.newArrayList();

    public OutputSink build() {
        final ImmutableList<Arg> outputArgs = ImmutableList.<Arg>builder()
                .addAll(args)
                .add(Args.outputUri(pathToOutput))
                .build();
        return () -> outputArgs;
    }
}
