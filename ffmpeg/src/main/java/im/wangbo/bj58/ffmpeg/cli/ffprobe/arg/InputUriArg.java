package im.wangbo.bj58.ffmpeg.cli.ffprobe.arg;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class InputUriArg implements FfprobeArg {
    @Override
    public String name() {
        return uri().toString();
    }

    @Override
    public String description() {
        return "Input file uri/path";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public abstract URI uri();

    public static InputUriArg of(final URI uri) {
        return new AutoValue_InputUriArg(uri);
    }
}
