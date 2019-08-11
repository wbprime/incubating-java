package im.wangbo.bj58.janus.schema.vertx.http;

import im.wangbo.bj58.janus.schema.utils.Events;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO more details here.
 * <p>
 * Created at 2019-08-10, by Elvis Wang
 */
final class EventBusHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EventBusHelper.class);

    private EventBusHelper() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    static <T> void sendEvent(
        final EventBus eventBus, final T msg, final Class<T> clz) {

        final String codecName = Events.codecName(clz);
        final String address = Events.address(clz);

        final DeliveryOptions opts = new DeliveryOptions();
        opts.setCodecName(Events.codecName(clz));

        LOG.debug("Send event codec({}), type({}) to \"{}\"", codecName, clz, address);

        eventBus.publish(address, msg, opts);
    }
}
