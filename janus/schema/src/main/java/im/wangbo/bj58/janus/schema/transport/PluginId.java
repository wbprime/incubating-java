package im.wangbo.bj58.janus.schema.transport;

import com.google.auto.value.AutoValue;

/**
 * TODO add brief description here
 *
 * @author Elvis Wang
 * @author Elvis Wang
 */
@AutoValue
public abstract class PluginId {
    public abstract long id();

    public static PluginId of(final long id) {
        return new AutoValue_PluginId(id);
    }
}
