package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

import im.wangbo.bj58.ffmpeg.arg.Arg;
import im.wangbo.bj58.ffmpeg.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.common.Value;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface FfmpegArg extends Arg {

    default String argName() {
        return "";
    }

    default Optional<String> argValue() {
        return Optional.empty();
    }

    default String description() {
        return "";
    }

    @Override
    default ArgSpec spec() {
        return ArgSpec.of(argName(), description());
    }

    @Override
    default Optional<Value> value() {
        return argValue().map(Value::ofString);
    }
}
