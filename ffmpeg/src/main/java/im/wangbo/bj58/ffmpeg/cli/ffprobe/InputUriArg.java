package im.wangbo.bj58.ffmpeg.cli.ffprobe;

import com.google.auto.value.AutoValue;
import im.wangbo.bj58.ffmpeg.arg.ArgSpec;
import im.wangbo.bj58.ffmpeg.arg.FfprobeArg;
import im.wangbo.bj58.ffmpeg.common.Value;
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
    public final ArgSpec spec() {
        return ArgSpec.of(
            uri().toString(),
            "Output file url."
        );
    }

    @Override
    public final Optional<Value> value() {
        return Optional.empty();
    }

    abstract URI uri();

    public static InputUriArg of(final URI uri) {
        return new AutoValue_InputUriArg(uri);
    }

    public static InputUriArg of(final Path path) {
        return of(path.toUri());
    }
}
