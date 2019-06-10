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
public class InputSourceBuilder {
    private String pathToInput;

    private List<Arg> args = Lists.newArrayList();

    public InputSource build() {
        final ImmutableList<Arg> inputArgs = ImmutableList.<Arg>builder()
                .addAll(args)
                .add(Args.inputUri(pathToInput))
                .build();
        return () -> inputArgs;
    }
}
