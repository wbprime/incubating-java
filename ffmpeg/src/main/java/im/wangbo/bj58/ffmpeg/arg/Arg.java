package im.wangbo.bj58.ffmpeg.arg;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface Arg {
    String argName();

    Optional<String> argValue();

    static Arg named(final String name) {
        return StdArg.of(name);
    }

    static Arg paired(final String name, final Value value) {
        return StdArg.of(name, value.encode());
    }

    static Arg paired(final String name, final String value) {
        return StdArg.of(name, value);
    }
}
