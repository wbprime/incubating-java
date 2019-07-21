package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg;

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
public abstract class InputUriArg implements InputArg {
    @Override
    public final String name() {
        return "-i";
    }

    @Override
    public final String description() {
        return "Input file url";
    }

    // Use String instead of URI here to avoid url encode
    abstract String uri();

    @Override
    public final Optional<String> value() {
        return Optional.of(uri());
    }

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
