package im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.main;

import com.google.auto.value.AutoValue;

import java.net.URI;
import java.util.Optional;

import im.wangbo.bj58.ffmpeg.cli.ffmpeg.arg.OutputArg;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class OutputUriArg implements OutputArg {
    @Override
    public final String name() {
        return uri().toString();
    }

    @Override
    public final String description() {
        return "Output file url";
    }

    abstract URI uri();

    @Override
    public final Optional<String> value() {
        return Optional.empty();
    }

    public static OutputUriArg of(final URI uri) {
        return new AutoValue_OutputUriArg(uri);
    }
}
