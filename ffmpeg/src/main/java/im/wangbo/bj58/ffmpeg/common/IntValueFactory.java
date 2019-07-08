package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public abstract class IntValueFactory implements ValueFactory<Integer> {
    @Override
    public Optional<Value> create(final Integer base, final Class<? extends Integer> clz) {
        return StdValue.of(String.valueOf(base));
    }
}
