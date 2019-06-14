package im.wangbo.bj58.ffmpeg.ffprobe;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class InputUriArg implements FfprobeArg {
    @Override
    public final String argName() {
        return uri().toString();
    }

    @Override
    public final String description() {
        return "Output file url";
    }

    abstract URI uri();

    @Override
    public final Optional<String> argValue() {
        return Optional.empty();
    }

    public static InputUriArg of(final URI uri) {
        return new AutoValue_InputUriArg(uri);
    }

    public static InputUriArg of(final Path path) {
        return of(path.toUri());
    }
}
