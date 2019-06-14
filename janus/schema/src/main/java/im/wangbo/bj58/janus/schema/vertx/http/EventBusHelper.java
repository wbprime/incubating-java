package im.wangbo.bj58.janus.schema.vertx.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import im.wangbo.bj58.janus.schema.vertx.event.AbstractEventTypeMeta;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class EventBusHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EventBusHelper.class);

    private final EventBus eventBus;

    public EventBusHelper(final EventBus eventBus) {
        this.eventBus = eventBus;

        eventBus.registerCodec(new JsonObjCodec());
        eventBus.registerCodec(new JsonArrCodec());
        eventBus.registerCodec(new SessionCreatedCodec());
        eventBus.registerCodec(new SessionDestroyedCodec());
        eventBus.registerCodec(new MessageSentCodec());
        eventBus.registerCodec(new MessageReceivedCodec());
    }

    public final <T> void sendEvent(final T msg, final Class<T> clz) {
        final AbstractEventTypeMeta<?> meta = AbstractEventTypeMeta.create(clz);

        final DeliveryOptions opts = new DeliveryOptions();
        opts.setCodecName(meta.codecName());

        LOG.info("Send event codec({}), type({}) to {}", meta.codecName(), meta.type(), meta.address());

        eventBus.publish(meta.address(), msg, opts);
    }

}
