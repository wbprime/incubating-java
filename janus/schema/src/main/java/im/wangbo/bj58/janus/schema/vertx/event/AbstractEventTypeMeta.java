package im.wangbo.bj58.janus.schema.vertx.event;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public abstract class AbstractEventTypeMeta<T> {
    private final Class<T> clz;

    @SuppressWarnings("unchecked")
    public AbstractEventTypeMeta() {
        final Type type = getClass().getGenericSuperclass();
        final ParameterizedType ptype = (ParameterizedType) type; // safe
        final Type typeArgument = ptype.getActualTypeArguments()[0]; // safe

        this.clz = (Class<T>) typeArgument; // safe
    }

    private AbstractEventTypeMeta(final Class<T> clz) {
        this.clz = clz;
    }

    public static <T> AbstractEventTypeMeta<T> create(final Class<T> clz) {
        return new AbstractEventTypeMeta<T>(clz) {
        };
    }

    public final String address() {
        return "eventbus/" + clz.getName();
    }

    public final String codecName() {
        return "codec/" + clz.getName();
    }
}
