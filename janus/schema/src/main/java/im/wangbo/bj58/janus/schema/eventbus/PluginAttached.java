package im.wangbo.bj58.janus.schema.eventbus;

import com.google.auto.value.AutoValue;

import javax.json.JsonObject;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 */
@AutoValue
public abstract class PluginAttached implements JsonableEvent {
    private static final String SESSION_ID = "sessionId";
    private static final String PLUGIN_HANDLE_ID = "pluginHandleId";

    public abstract long getSessionId();

    public abstract long getPluginHandleId();

    @Override
    public final JsonObject json() {
        return JsonObject.EMPTY_JSON_OBJECT;
    }

    public static PluginAttached create(final long sessionId, final long pluginHandleId) {
        return new AutoValue_PluginAttached(sessionId, pluginHandleId);
    }
}
