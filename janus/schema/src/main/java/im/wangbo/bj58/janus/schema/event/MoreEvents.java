package im.wangbo.bj58.janus.schema.event;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class MoreEvents {
    private MoreEvents() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static final String TYPE_MESSAGE_SENT = "event.message.sent";
    public static final String TYPE_MESSAGE_RECEIVED = "event.message.received";
    public static final String TYPE_SESSION_CREATED = "event.session.created";
    public static final String TYPE_SESSION_DESTROYED = "event.session.destroyed";
    public static final String TYPE_PLUGIN_HANDLE_ATTACHED = "event.handle.attached";
    public static final String TYPE_PLUGIN_HANDLE_DETACHED = "event.handle.detached";

    public static final String KEY_SESSION_ID = "session_id";

    public static final String KEY_PLUGIN_HANDLE_ID = "handle_id";
}
