package im.wangbo.bj58.janus.schema.eventbus;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class MoreEvents {
    private MoreEvents() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static final String TYPE_MESSAGE_SENT = "eventType.message.sent";
    public static final String TYPE_MESSAGE_RECEIVED = "eventType.message.received";
    public static final String TYPE_SESSION_CREATED = "eventType.session.created";
    public static final String TYPE_SESSION_DESTROYED = "eventType.session.destroyed";
    public static final String TYPE_PLUGIN_HANDLE_ATTACHED = "eventType.handle.attached";
    public static final String TYPE_PLUGIN_HANDLE_DETACHED = "eventType.handle.detached";

    public static final String KEY_SESSION_ID = "session_id";

    public static final String KEY_PLUGIN_HANDLE_ID = "handle_id";
}
