package im.wangbo.bj58.janus.schema.vertx.http;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
final class Events {
    private static final Logger LOG = LoggerFactory.getLogger(Events.class);

    private Events() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static <T> void sendEvent(
        final EventBus eventBus, final T msg, final Class<T> clz) {

        final String codecName = codecName(clz);
        final String address = address(clz);

        final DeliveryOptions opts = new DeliveryOptions();
        opts.setCodecName(codecName(clz));

        LOG.debug("Send event codec({}), type({}) to \"{}\"", codecName, clz, address);

        eventBus.publish(address, msg, opts);
    }

    static String address(final Class<?> clz) {
        return "e/" + clz.getName();
    }

    tatic String codecName(final Class<?> clz) {
        return "c/" + clz.getName();
    }
}
