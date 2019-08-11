package im.wangbo.bj58.janus.schema.client;

import im.wangbo.bj58.janus.schema.event.MoreEvents;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
public final class JanusEvents {
    private JanusEvents() {
        throw new UnsupportedOperationException("Construction forbidden");
    }

    public static final String TYPE_JANUS_UNRECOGNIZED = "event.janus.un_recognized";

    public static final String TYPE_JANUS_SERVERINFO = "event.janus.server_info";
    public static final String TYPE_JANUS_ACK = "event.janus.ack";
    public static final String TYPE_JANUS_SUCCESS = "event.janus.success";
    public static final String TYPE_JANUS_ERROR = "event.janus.error";
    public static final String TYPE_JANUS_NOTIFY = "event.janus.notify";

    public static final String TYPE_JANUS_WEBRTC_UP = "event.webrtc.up";
    public static final String TYPE_JANUS_WEBRTC_DOWN = "event.webrtc.down";
    public static final String TYPE_JANUS_WEBRTC_MEDIA = "event.webrtc.media";
    public static final String TYPE_JANUS_WEBRTC_SLOWLINK = "event.webrtc.slowlink";

    public static final String KEY_TRANSACTION_ID = MoreEvents.KEY_TRANSACTION_ID;
    public static final String KEY_TRACE_ID = MoreEvents.KEY_TRACE_ID;
    public static final String KEY_SESSION_ID = MoreEvents.KEY_SESSION_ID;
    public static final String KEY_PLUGINHANDLE_ID = MoreEvents.KEY_PLUGINHANDLE_ID;
    public static final String KEY_DATA = MoreEvents.KEY_DATA;

    public static final String KEY_DATA_MESSAGE = "message";
    public static final String KEY_DATA_MEDIA_TYPE = "media_type";
    public static final String KEY_DATA_RECEIVING = "receiving";
    public static final String KEY_DATA_NUM_OF_NACKS = "nacks";
    public static final String KEY_DATA_UPLINK = "uplink";

    public static final String KEY_DATA_ERR_CODE = "err_code";
    public static final String KEY_DATA_ERR_MSG = "err_message";
}
