package im.wangbo.bj58.ffmpeg.cli.ffprobe.arg;

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
    public String name() {
        return uri();
    }

    @Override
    public String description() {
        return "Input file uri/path";
    }

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    // Use String instead of URI here to avoid url encode
    abstract String uri();

    public static InputUriArg of(final Path path) {
        return of(path.toString());
    }

    public static InputUriArg of(final URI uri) {
        return of(uri.toString());
    }

    public static InputUriArg of(final String uri) {
        return new AutoValue_InputUriArg(uri);
    }
}
