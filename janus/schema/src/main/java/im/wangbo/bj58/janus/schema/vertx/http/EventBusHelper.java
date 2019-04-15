package im.wangbo.bj58.janus.schema.vertx.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
class EventBusHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EventBusHelper.class);

    public static final String EVENT_BUS_EVENT_TYPE_KEY = "message_type";

    private final Vertx vertx;

    public EventBusHelper(final Vertx vertx) {
        this.vertx = vertx;

        vertx.eventBus().registerCodec(new JsonObjCodec());
        vertx.eventBus().registerCodec(new JsonArrCodec());
        vertx.eventBus().registerCodec(new SessionCreatedCodec());
        vertx.eventBus().registerCodec(new SessionDestroyedCodec());
        vertx.eventBus().registerCodec(new MessageSentCodec());
        vertx.eventBus().registerCodec(new MessageReceivedCodec());
    }

    public final <T> void sendEvent(final T msg, final Class<T> clz) {
        final EventTypeMeta<?> meta = EventTypeMeta.create(clz);

        final DeliveryOptions opts = new DeliveryOptions();
        opts.addHeader(EVENT_BUS_EVENT_TYPE_KEY, meta.type());
        opts.setCodecName(meta.codecName());

        LOG.info("Send event codec({}), type({}) to {}", meta.codecName(), meta.type(), meta.address());

        vertx.eventBus().publish(meta.address(), msg, opts);
    }

}
