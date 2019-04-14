package im.wangbo.bj58.janus.schema.eventbus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public abstract class EventTypeMeta<T> {
    private final Class<T> clz;

    @SuppressWarnings("unchecked")
    public EventTypeMeta() {
        final Type type = getClass().getGenericSuperclass();
        final ParameterizedType ptype = (ParameterizedType) type; // safe
        final Type typeArgument = ptype.getActualTypeArguments()[0]; // safe

        this.clz = (Class<T>) typeArgument; // safe
    }

    private EventTypeMeta(final Class<T> clz) {
        this.clz = clz;
    }

    public static <T> EventTypeMeta<T> create(final Class<T> clz) {
        return new EventTypeMeta<T>(clz) {
        };
    }

    public final String address() {
        return "eventbus/" + clz.getName();
    }

    public final String codecName() {
        return "codec/" + clz.getName();
    }

    public final String type() {
        final Class<?> tmp = clz.getEnclosingClass();
        return null != tmp ? tmp.getSimpleName() : clz.getSimpleName();
    }
}
