package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.InputArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class InputUriArg implements InputArg {
    @Override
    public final String argName() {
        return "-i";
    }

    @Override
    public final String description() {
        return "Input file url";
    }

    abstract URI uri();

    @Override
    public final Optional<String> argValue() {
        return Optional.of(uri().toString());
    }

    public static InputUriArg of(final URI uri) {
        return new AutoValue_InputUriArg(uri);
    }
}
