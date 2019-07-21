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
public abstract class OutputUriArg implements OutputArg {
    @Override
    public final String name() {
        return uri();
    }

    @Override
    public final String description() {
        return "Output file url";
    }

    // Use String instead of URI here to avoid url encode
    abstract String uri();

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static OutputUriArg of(final Path path) {
        return of(path.toString());
    }

    public static OutputUriArg of(final URI uri) {
        return of(uri.toString());
    }

    public static OutputUriArg of(final String uri) {
        return new AutoValue_OutputUriArg(uri);
    }
}
