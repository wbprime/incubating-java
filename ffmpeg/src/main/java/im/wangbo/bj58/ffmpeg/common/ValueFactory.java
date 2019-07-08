package im.wangbo.bj58.ffmpeg.common;

import java.util.Optional;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public interface ValueFactory<T> {
    Optional<Value> create(final T base, final Class<? extends T> clz);
}
