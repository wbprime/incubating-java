package im.wangbo.bj58.ffmpeg.ffprobe;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public abstract class WriterArg implements FfprobeArg {
    @Override
    public String argName() {
        return "-of";
    }

    @Override
    public Optional<String> argValue() {
        return Optional.empty();
    }

    @Override
    public String description() {
        return "Set the output printing format. " +
                "writer_name specifies the name of the writer, and writer_options specifies the options to be passed to the writer.";
    }
}
